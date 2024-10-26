package com.tywinlanni.hydra.repo

import com.benasher44.uuid.uuid4
import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.models.HydraProductPlanedForPurchase
import com.github.tywinlanni.hydra.common.models.HydraProductType
import com.github.tywinlanni.hydra.common.repo.*
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class ProductRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : ProductRepoBase(), IRepoProduct, IRepoProductInitializable {

    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, ProductEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun save(products: Collection<HydraProduct>) = products.map { product ->
        val entity = ProductEntity(product)
        require(entity.id != null)
        cache.put(entity.id, entity)
        product
    }

    override suspend fun createProduct(rq: DbProductRequest): IDbProductResponse = tryProductMethod {
        val key = randomUuid()
        val product = rq.product.copy(id = HydraProductId(key))
        val entity = ProductEntity(product)
        mutex.withLock {
            cache.put(key, entity)
        }
        DbProductResponseOk(product)
    }

    override suspend fun readProduct(rq: DbProductIdRequest): IDbProductResponse = tryProductMethod {
        val key = rq.id.takeIf { it != HydraProductId.NONE }?.asString() ?: return@tryProductMethod errorEmptyId
        mutex.withLock {
            cache.get(key)
                ?.let {
                    DbProductResponseOk(it.toInternal())
                } ?: errorNotFound(rq.id)
        }
    }

    override suspend fun updateProduct(rq: DbProductRequest): IDbProductResponse = tryProductMethod {
        val rqProduct = rq.product
        val id = rqProduct.id.takeIf { it != HydraProductId.NONE } ?: return@tryProductMethod errorEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldProduct = cache.get(key)?.toInternal()
            when {
                oldProduct == null -> errorNotFound(id)
                else -> {
                    val newProduct = rqProduct.copy()
                    val entity = ProductEntity(newProduct)
                    cache.put(key, entity)
                    DbProductResponseOk(newProduct)
                }
            }
        }
    }


    override suspend fun deleteProduct(rq: DbProductIdRequest): IDbProductResponse = tryProductMethod {
        val id = rq.id.takeIf { it != HydraProductId.NONE } ?: return@tryProductMethod errorEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldProduct = cache.get(key)?.toInternal()
            when {
                oldProduct == null -> errorNotFound(id)
                else -> {
                    cache.invalidate(key)
                    DbProductResponseOk(oldProduct)
                }
            }
        }
    }

    override suspend fun searchProduct(rq: DbProductFilterRequest): IDbProductsResponse = tryProductsMethod {
        val result: List<HydraProduct> = cache.asMap().asSequence()
            .filter { entry ->
                rq.planedForPurchase.takeIf { it != HydraProductPlanedForPurchase.NONE }?.let {
                    it.toBoolean() == entry.value.planedForPurchase
                } ?: true
            }
            .filter { entry ->
                rq.productType.takeIf { it != HydraProductType.NONE }?.let {
                    it.name == entry.value.productType
                } ?: true
            }
            .filter { entry ->
                rq.nameFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.name?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        DbProductsResponseOk(result)
    }
}
