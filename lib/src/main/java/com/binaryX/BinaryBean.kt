package com.binaryX

data class BinaryBean(
    val code: Int,
    val `data`: Data
)

data class Data(
    val error: Any,
    val result: Result
)

data class Result(
    val items: List<Item>,
    val total: Int
)

data class Item(
    val agility: Int,
    val block_number: Int,
    val brains: Int,
    val buyer: String,
    val career_address: String,
    val charm: Int,
    val level: Int,
    val name: String,
    val order_id: String,
    val pay_addr: String,
    val physique: Int,
    val price: String,
    val seller: String,
    val strength: Int,
    val token_id: String,
    val total: Int,
    val volition: Int
)