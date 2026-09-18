package com.christa.lib

fun main() {
    val menuRepository = MenuRepository()
    val orderRepository = OrderRepository()

    val console = ConsoleUI(menuRepository, orderRepository)

    console.start()
}