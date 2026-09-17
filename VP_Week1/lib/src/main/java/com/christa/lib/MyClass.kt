package com.christa.lib

import java.util.UUID

data class itemMenu (
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var description: String,
    var price: Double
)

data class itemOrder (
    val itemMenu: itemMenu,
    val quantity: Int
)

data class order(
    val id: String = UUID.randomUUID().toString(),
    val customerName: String,
    val items: List<itemOrder>,
    val totalPrice: Double
)