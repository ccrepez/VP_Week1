package com.christa.wizardadventure2

interface GameInterface {
    fun getPlayer(): Player
    fun initializePlayer(name: String)
    fun renamePlayer(newName: String)
    fun drinkHpPotion(): String
    fun drinkMpPotion(): String
    fun generateEnemy(): Enemy
    fun attackEnemy(enemy: Enemy, attackType: ElementType): Int
    fun triggerLifesteal(): Int
    fun takeEnemyDamage(): Int
    fun updateKillsAndCheckEvolution(): String
}