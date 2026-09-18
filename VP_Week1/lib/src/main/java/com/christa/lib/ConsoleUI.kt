package com.christa.lib

class ConsoleUI (
    private val menuRepository: MenuInterface,
    private val orderRepository: OrderRepository
) {
    fun start() {
        var isRunning = true
        while (isRunning) {
            println("=== RESTAURANT SYSTEM ===")
            println("1. Make Order")
            println("2. View Orders")
            println("3. View Menu")
            println("4. Add Menu")
            println("5. Edit Menu")
            println("6. Delete Menu")
            println("7. Exit")
            print("Choose an option: ")
            val inputString = readLine()
            println()

            try {
                val input = inputString!!.toInt()

                if (input == 1) {
                    makeOrder()
                } else if (input == 2) {
                    viewOrders()
                } else if (input == 3) {
                    viewMenu()
                } else if (input == 4) {
                    addMenu()
                } else if (input == 5) {
                    editMenu()
                } else if (input == 6) {
                    deleteMenu()
                } else if (input == 7) {
                    isRunning = false
                } else {
                    println("Invalid input.")
                }
            } catch (e: NumberFormatException) {
                println("Invalid input! Please input a number.")
            }
        }
    }

    private fun viewOrders() {
        println("=== ALL ORDERS ===")
        val orders = orderRepository.viewOrders()
        if (orders.isEmpty()) {
            println("No orders yet!")
            return
        }
        for (order in orders) {
            println("------ ${order.customerName}'s ORDER ------")
            for (i in 0 until order.items.size) {
                val pesanan = order.items[i]
                println("${i+1}. ${pesanan.itemMenu.name} x${pesanan.quantity} $${pesanan.itemMenu.price}")
            }
            println("---------------------------------")
            println("TOTAL $${order.totalPrice}")
        }
    }

    private fun viewMenu() {
        println("=== CURRENT MENU ===")
        val menus = menuRepository.viewMenu()
        if (menus.isEmpty()) {
            println("Menu is empty!")
            return
        }
        for (i in 0 until menus.size) {
            val item = menus[i]
            println("${i+1}. ${item.name} - ${item.price}")
            println(" Desc: ${item.description}")
        }
    }

    private fun addMenu() {
        println("=== ADD MENU ===")
        print("Enter food name: ")
        val name = readLine()
        print("Enter description: ")
        val desc = readLine()

        var price: Double? = null
        while (price == null) {
            print("Enter price: $")
            val inputString = readLine()

            try {
                price = inputString!!.toDouble()

                if (price < 0) {
                    println("Invalid price!")
                    price = null
                }
            } catch (e: NumberFormatException) {
                println("Invalid input! Please input a number.")
            }
        }

        val newItem = ItemMenu(name = name!!, description = desc!!, price = price)
        val response = menuRepository.addMenu(newItem)
        println(response)
    }

    private fun editMenu() {
        viewMenu()
        val menus = menuRepository.viewMenu()
        if (menus.isEmpty()) return

        print("Select menu number to edit (0 to cancel): ")
        val indexString = readLine()
        var index = -2

        try {
            index = indexString!!.toInt() - 1
        } catch (e: NumberFormatException) {

        }

        if (index == -1) {
            println("Editing cancelled.")
            return
        }

        if (index >= 0 && index < menus.size) {
            val itemToEdit = menus[index]

            print("Enter new name (leave blank to keep current): ")
            val newName = readLine()

            print("Enter new description (leave blank to keep current): ")
            val newDesc = readLine()

            print("Enter new price (leave blank to keep current): ")
            val priceString = readLine()
            var newPrice: Double? = null

            if (priceString != null && priceString != "") {
                try {
                    newPrice = priceString.toDouble()
                } catch (e: NumberFormatException) {
                    println("Invalid input! Please input a number.")
                }
            }

            val success = menuRepository.editMenu(itemToEdit.id, newName, newDesc, newPrice)

            if (success) {
                println("Menu updated successfully!")
            } else {
                println("Failed to update.")
            }
        } else {
            println("Invalid menu number!")
        }
    }

    private fun deleteMenu() {
        viewMenu()
        val menus = menuRepository.viewMenu()
        if (menus.isEmpty()) return

        print("Select menu number to delete (0 to cancel): ")
        val indexString = readLine()
        var index = -2

        try {
            index = indexString!!.toInt() - 1
        } catch (e: NumberFormatException) {

        }

        if (index == -1) {
            println("Editing cancelled.")
            return
        }

        if (index >= 0 && index < menus.size) {
            val itemToDelete = menus[index]
            val success = menuRepository.deleteMenu(itemToDelete.id)

            if (success) {
                println("${itemToDelete.name} has been deleted.")
            } else {
                println("Failed to delete.")
            }
        } else {
            println("Invalid menu number!")
        }
    }

    private fun makeOrder() {
        val menus = menuRepository.viewMenu()
        if (menus.isEmpty()) {
            println("Cannot make order, menu is empty!")
            return
        }

        print("Enter customer name: ")
        val customerName = readLine()
        val currentOrderItems = ArrayList<ItemOrder>()
        var ordering = true

        while (ordering) {
            viewMenu()
            print("Select menu number to order (0 to finish ordering): ")
            val indexString = readLine()
            var index = -2

            try {
                index = indexString!!.toInt() - 1
            } catch (e: NumberFormatException) {

            }

            if (index == -1) {
                ordering = false
            } else if (index >= 0 && index < menus.size) {
                print("Enter quantity for ${menus[index].name}: ")
                val qtyString = readLine()
                var qty = 0

                try {
                    qty = qtyString!!.toInt()
                } catch (e: NumberFormatException) {

                }

                if (qty > 0) {
                    currentOrderItems.add(ItemOrder(menus[index], qty))
                    println("Added to order!")
                } else {
                    println("Invalid quantity!")
                }
            } else {
                println("Invalid menu number!")
            }
        }

        if (currentOrderItems.isNotEmpty()) {
            var total = 0.0
            for (i in 0 until currentOrderItems.size) {
                val pesanan = currentOrderItems[i]
                total += pesanan.itemMenu.price * pesanan.quantity
            }

            val newOrder = Order(customerName = customerName!!, items = currentOrderItems, totalPrice = total)
            val response = orderRepository.addOrder(newOrder)
            println("$response Total: $$total")
        } else {
            println("Order cancelled (no items selected).")
        }
    }
}