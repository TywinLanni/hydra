package com.github.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.helpers.asHydraError
import com.github.tywinlanni.hydra.common.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.github.tywinlanni.hydra.common.repo.*
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.ClientSession
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList


class ProductRepoMongo(
    properties: MongoProperties,
    private val randomUuid: () -> String
) : IRepoProduct, IRepoProductInitializable {
    private val conn: MongoClient = MongoClient.create(properties.url)
    
    private val database = conn.getDatabase(properties.database)
    private val products = database.getCollection<HydraProduct>(properties.collection)

    suspend fun clear(): Unit = withContext(Dispatchers.IO) {
        database.drop()
    }

    private suspend fun saveObj(product: HydraProduct, session: ClientSession?): HydraProduct = withContext(Dispatchers.IO) {
        session?.let {
            products.insertOne(session, product)
        } ?: products.insertOne(product)

        product
    }

    private suspend inline fun <T> transactionWrapper(crossinline block: suspend (ClientSession) -> T, crossinline handle: (Exception) -> T): T =
        withContext(Dispatchers.IO) {
            conn.startSession().use { session ->
                session.startTransaction()
                try {
                    val res = block(session)
                    session.commitTransaction()
                    res
                } catch (e: Exception) {
                    session.abortTransaction()
                    handle(e)
                }
            }
        }

    override suspend fun save(products: Collection<HydraProduct>): Collection<HydraProduct> =
        products.map { saveObj(it, null) }

    override suspend fun createProduct(rq: DbProductRequest): IDbProductResponse = transactionWrapper ({
        DbProductResponseOk(saveObj(rq.product, it))
    }) { DbProductResponseErr(it.asHydraError()) }

    private suspend fun read(id: HydraProductId, session: ClientSession): IDbProductResponse {
        val res = products.find(session, eq(HydraProduct::id.name, id.asString()))
            .firstOrNull()
            ?: return errorNotFound(id)

        return DbProductResponseOk(res)
    }

     override suspend fun readProduct(rq: DbProductIdRequest): IDbProductResponse =
         transactionWrapper ({ read(rq.id, session = it) }) { DbProductResponseErr(it.asHydraError()) }

    private suspend fun update(
        id: HydraProductId,
        lock: HydraProductLock,
        block: suspend (HydraProduct, ClientSession) -> IDbProductResponse
    ): IDbProductResponse =
        transactionWrapper ({ session ->
            if (id == HydraProductId.NONE) return@transactionWrapper errorEmptyId

            val current = products.find(eq(HydraProduct::id.name, id.asString()))
                .firstOrNull()

            when {
                current == null -> errorNotFound(id)
                current.lock != lock -> errorRepoConcurrency(current, lock)
                else -> block(current, session)
            }
        }) { DbProductResponseErr(it.asHydraError()) }

     override suspend fun updateProduct(rq: DbProductRequest): IDbProductResponse = update(rq.product.id, rq.product.lock) { product, session ->
        products.replaceOne(session, eq(HydraProduct::id.name, rq.product.id.asString()), product.copy(lock = HydraProductLock(randomUuid())))
        read(rq.product.id, session)
    }

     override suspend fun deleteProduct(rq: DbProductIdRequest): IDbProductResponse = transactionWrapper ({ session ->
        val data = products.findOneAndDelete(session, eq(HydraProduct::id.name, rq.id.asString()))
            ?: return@transactionWrapper errorNotFound(rq.id)
        DbProductResponseOk(data)
    }) { DbProductResponseErr(it.asHydraError()) }

     override suspend fun searchProduct(rq: DbProductFilterRequest): IDbProductsResponse =
        transactionWrapper({
            val res = products.find(it, and(
                if (rq.nameFilter.isNotBlank()) {
                    eq(HydraProduct::name.name, rq.nameFilter)
                } else null,
                if (rq.productType != HydraProductType.NONE) {
                    eq(HydraProduct::productType.name, rq.productType)
                } else null,
                if (rq.planedForPurchase != HydraProductPlanedForPurchase.NONE) {
                    eq(HydraProduct::planedForPurchase.name, rq.planedForPurchase)
                } else null
            )).toList()

            DbProductsResponseOk(data = res)
        }, {
            DbProductsResponseErr(it.asHydraError())
        })
}
