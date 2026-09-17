package com.christa.lib

class MenuRepository : MenuInterface {
    private val itemsMenu = ArrayList<ItemMenu>()

    override fun viewMenu(): List<ItemMenu> {
        return itemsMenu
    }

    override fun addMenu(itemMenu: ItemMenu): String {
        itemsMenu.add(itemMenu)
        return "Item added successfully!"
    }

    override fun editMenu(
        id: String,
        newName: String?,
        newDesc: String?,
        newPrice: Double?
    ): Boolean {
        val item = itemsMenu.find { makanan -> makanan.id == id } ?: return false

        if (newName != null && newName != "") {
            item.name = newName
        }

        if (newDesc != null && newDesc != "") {
            item.description = newDesc
        }

        if (newPrice != null && newPrice >= 0) {
            item.price = newPrice
        }
        return true
    }

    override fun deleteMenu(id: String): Boolean {
        return itemsMenu.removeIf { makanan -> makanan.id == id }
    }

}