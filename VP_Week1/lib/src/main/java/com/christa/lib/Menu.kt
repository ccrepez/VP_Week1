package com.christa.lib

import java.util.UUID

data class ItemMenu (
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var description: String,
    var price: Double
)

data class ItemOrder (
    val itemMenu: ItemMenu,
    val quantity: Int
)

data class Order(
    val id: String = UUID.randomUUID().toString(),
    val customerName: String,
    val items: List<ItemOrder>,
    val totalPrice: Double
)