package com.christa.wizardadventure2

import kotlin.random.Random
import kotlin.random.nextInt

class GameRepository : GameInterface {
    private var player = Player("Wizard")

    override fun getPlayer(): Player {
        return player
    }

    override fun initializePlayer(name: String) {
        player = Player(name = name)
    }

    override fun renamePlayer(newName: String) {
        player.name = newName
    }

    override fun drinkHpPotion(): String {
        if (player.hpPots > 0) {
            player.hpPots -= 1
            player.hp += 25

            if (player.hp > player.maxHp) {
                player.hp = player.maxHp
            }

            return "Drank HP Potion! HP now: ${player.hp}/${player.maxHp}"
        }
        return "No HP Potion left!"
    }

    override fun drinkMpPotion(): String {
        if (player.mpPots > 0) {
            player.mpPots -= 1
            player.mana += 15

            if (player.mana > player.maxMana) {
                player.mana = player.maxMana
            }

            return "Drank MP Potion! MP now: ${player.mana}/${player.maxMana}"
        }
        return "No MP Potion left!"
    }

    override fun generateEnemy(): Enemy {
        val types = ElementType.values()
        val randomType = types[Random.nextInt(types.size)]
        return Enemy(type = randomType, name = "${randomType}mon")
    }

    override fun attackEnemy(enemy: Enemy, attackType: ElementType): Int {
        player.mana -= 10
        var damage = player.baseDamage

        val isSuperEffective = (attackType == ElementType.FIRE && enemy.type == ElementType.GRASS) ||
                (attackType == ElementType.WATER && enemy.type == ElementType.FIRE) ||
                (attackType == ElementType.GRASS && enemy.type == ElementType.WATER)

        if (isSuperEffective) {
            damage += 2
        }

        enemy.hp -= damage
        return damage
    }

    override fun triggerLifesteal(): Int {
        if (player.isEvolved && player.lifesteal > 0) {
            val heal = player.lifesteal

            player.hp += heal

            if (player.hp > player.maxHp) {
                player.hp = player.maxHp
            }

            return heal
        }
        return 0
    }

    override fun takeEnemyDamage(): Int {
        player.hp -= 10
        return 10
    }

    override fun updateKillsAndCheckEvolution(): String {
        player.kills += 1
        var message = "You defeated an enemy! Total kills: ${player.kills}."

        if (!player.isEvolved && player.kills >= 5) {
            player.isEvolved = true
            player.maxHp = (player.maxHp * 1.5).toInt()
            player.hp = player.maxHp
            player.maxMana = (player.maxMana * 1.5).toInt()
            player.mana = player.maxMana
            player.baseDamage = (player.baseDamage * 1.5).toInt()
            player.lifesteal = 1
            message += "[EVOLUTION] You have became a Strong Wizard! HP & Mana recovered! Lifesteal skill is opened (Recovered 1 HP per attack)"
        } else if (player.isEvolved) {
            player.lifesteal += 1
            message += "Lifesteal increased to ${player.lifesteal}!"
        }

        return message
    }
}