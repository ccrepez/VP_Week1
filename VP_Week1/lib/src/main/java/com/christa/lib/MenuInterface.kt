package com.christa.lib

interface MenuInterface {
    fun viewMenu(): List<ItemMenu>
    fun addMenu(itemMenu: ItemMenu): String
    fun editMenu(id: String, newName: String?, newDesc: String?, newPrice: Double?): Boolean
    fun deleteMenu(id: String): Boolean
}