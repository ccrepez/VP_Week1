package com.christa.wizardadventure2

enum class ElementType {
    FIRE,
    WATER,
    GRASS
}

data class Player(
    var name: String,
    var maxHp: Int = 50,
    var hp: Int = 50,
    var maxMana: Int = 30,
    var mana: Int = 30,
    var kills: Int = 0,
    var hpPots: Int = 5,
    var mpPots: Int = 5,
    var baseDamage: Int = 10,
    var isEvolved: Boolean = false,
    var lifesteal: Int = 0
)

data class Enemy(
    val type: ElementType,
    val name: String,
    var maxHp: Int = 30,
    var hp: Int = 30,
    val damage: Int = 10
)