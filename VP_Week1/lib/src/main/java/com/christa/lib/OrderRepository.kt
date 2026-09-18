package com.christa.lib

class OrderRepository : OrderInterface {
    private val orders = ArrayList<Order>()

    override fun viewOrders(): List<Order> {
        return orders
    }

    override fun addOrder(order: Order): String {
        orders.add(order)
        return "Order for ${order.customerName} placed successfully!"
    }
}