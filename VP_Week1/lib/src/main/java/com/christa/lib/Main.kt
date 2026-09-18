package com.christa.lib

fun main() {
    val menuRepository = MenuRepository()
    val orderRepository = OrderRepository()

    menuRepository.addMenu(ItemMenu(name = "Nasi Goreng", description = "Pedas", price = 150.0))
    menuRepository.addMenu(ItemMenu(name = "Es Teh", description = "Manis", price = 50.0))

    val console = ConsoleUI(menuRepository, orderRepository)

    console.start()
}