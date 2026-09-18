package com.christa.wizardadventure2

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
}