package com.github.tywinlanni

import com.github.tywinlanni.hydra.repo.*

class ProductRepoInMemoryCreateTest : RepoProductCreateTest() {
    override val repo = ProductRepoInitialized(
        ProductRepoInMemory(randomUuid = { uuidNew.asString() }),
        initObjects = initObjects,
    )
}

class ProductRepoInMemoryDeleteTest : RepoProductDeleteTest() {
    override val repo = ProductRepoInitialized(
        ProductRepoInMemory(),
        initObjects = initObjects,
    )
}

class ProductRepoInMemoryReadTest : RepoProductReadTest() {
    override val repo = ProductRepoInitialized(
        ProductRepoInMemory(),
        initObjects = initObjects,
    )
}

class ProductRepoInMemorySearchTest : RepoProductSearchTest() {
    override val repo = ProductRepoInitialized(
        ProductRepoInMemory(),
        initObjects = initObjects,
    )
}

class ProductRepoInMemoryUpdateTest : RepoProductUpdateTest() {
    override val repo = ProductRepoInitialized(
        ProductRepoInMemory(),
        initObjects = initObjects,
    )
}
