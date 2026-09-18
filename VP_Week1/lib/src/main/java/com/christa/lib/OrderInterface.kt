package com.christa.lib

interface OrderInterface {
    fun viewOrders(): List<Order>
    fun addOrder(order: Order): String
}