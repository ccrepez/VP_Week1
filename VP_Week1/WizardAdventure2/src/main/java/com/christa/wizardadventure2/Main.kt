package com.christa.wizardadventure2

fun main() {
    val gameRepository = GameRepository()
    val consoleUI = ConsoleUI(gameRepository)

    consoleUI.start()
}