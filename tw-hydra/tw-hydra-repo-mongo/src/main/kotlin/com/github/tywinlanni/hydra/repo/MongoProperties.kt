package com.github.tywinlanni.hydra.repo

data class MongoProperties(
    val host: String = "localhost",
    val port: Int = 27017,
    val user: String = "hydra-user",
    val password: String = "password",
    val database: String = "hydra",
    val collection: String = "products",
) {
    val url: String
        get() = "mongodb://$user:$password@$host:$port/$database"
}
