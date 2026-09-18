package com.christa.wizardadventure2

class ConsoleUI(private val gameRepository: GameInterface) {

    fun start() {
        setupNewPlayer()
        var isRunning = true

        while (isRunning) {
            val player = gameRepository.getPlayer()

            if (player.hp <= 0) {
                println("GAME OVER!")
                println("Start from the beginning...")

                setupNewPlayer()
                continue
            }

            println("What are you going to do?")
            println("1. View Stats")
            println("2. Enter Battle")
            println("3. Give Up (Exit)")
            print("> ")

            val inputString = readLine()
            var choice = -1

            try {
                choice = inputString!!.toInt()
            } catch (e: NumberFormatException) {

            }

            if (choice == 1) {
                viewStats()
            } else if (choice == 2) {
                enterBattle()
            } else if (choice == 3) {
                isRunning = false
            } else {
                println("Invalid input!")
            }
        }
    }

    private fun setupNewPlayer() {
        println("What is your name?")
        print("> ")
        val name = readLine() ?: "Wizard"
        val finalName = if (name.isEmpty()) "Wizard" else name
        gameRepository.initializePlayer(finalName)
        println("Good luck, $finalName!")
    }

    private fun viewStats() {
        var viewing = true
        while (viewing) {
            val p = gameRepository.getPlayer()
            println("--- ${p.name}'s STATS ---")
            println("HP: ${p.hp}/ ${p.maxHp}")
            println("Mana: ${p.mana}/ ${p.maxMana}")
            if (p.isEvolved) {
                println("Status: Strong Wizard (Lifesteal: ${p.lifesteal})")
            } else {
                println("Kills needed to evolve: ${p.kills}/ 5")
            }
            println("Mana Potions held: ${p.mpPots}")
            println("Health Potions held: ${p.hpPots}")
            println("------------------------")
            println("a. Drink Mana Potion")
            println("b. Drink Health Potion")
            println("c. Rename self")
            println("d. Back")
            print("> ")
            println()

            val choice = readLine()

            if (choice == "a") {
                println(gameRepository.drinkMpPotion())
            } else if (choice == "b") {
                println(gameRepository.drinkHpPotion())
            } else if (choice == "c") {
                print("Enter new name: ")
                val newName = readLine()
                if (!newName.isNullOrBlank()) {
                    gameRepository.renamePlayer(newName)
                    println("Name is renamed to $newName!")
                }
            } else if (choice == "d") {
                viewing = false
            } else {
                println("Invalid input!")
            }
        }
    }

    private fun enterBattle() {
        val enemy = gameRepository.generateEnemy()
        println("A wild enemy has appeared!")

        var inBattle = true
        while (inBattle) {
            val p = gameRepository.getPlayer()
            println("--- BATTLE ---")
            println("${p.name}")
            println("HP: ${p.hp}/ ${p.maxHp} | Mana: ${p.mana}/ ${p.maxMana}")
            println("HP Pots: ${p.hpPots} | MP Pots: ${p.mpPots}")

            println("${enemy.name}")
            println("HP: ${enemy.hp}/ ${enemy.maxHp} | Type: ${enemy.type}")
            println("-------------------")
            println("a. Fire Attack (10 Mana)")
            println("b. Water Attack (10 Mana)")
            println("c. Grass Attack (10 Mana)")
            println("d. Drink Potion")
            println("e. Run")
            print("> ")

            val choice = readLine()
            var playerActed = false

            if (choice == "a" || choice == "b" || choice == "c") {
                if (p.mana < 10) {
                    println("Mana is not enough!")
                } else {
                    val attackType = if (choice == "a") {
                        ElementType.FIRE
                    } else if (choice == "b") {
                        ElementType.WATER
                    } else {
                        ElementType.GRASS
                    }

                    val dmg = gameRepository.attackEnemy(enemy, attackType)
                    println("You attacked! Gave $dmg to ${enemy.name}")

                    val heal = gameRepository.triggerLifesteal()
                    if (heal > 0) println("Lifesteal heals you $heal HP!")

                    playerActed = true
                }
            } else if (choice == "d") {
                println("1. HP Potion | 2. MP Potion | 3. Cancel")
                print("> ")
                val potionChoice = readLine()

                if (potionChoice == "1") {
                    println(gameRepository.drinkHpPotion())
                    playerActed = true
                } else if (potionChoice == "2") {
                    println(gameRepository.drinkMpPotion())
                    playerActed = true
                }
            } else if (choice == "e") {
                println("You escaped safely!")
                inBattle = false
            } else {
                println("Invalid input!")
            }

            if (playerActed && enemy.hp > 0) {
                val dmgTaken = gameRepository.takeEnemyDamage()
                println("${enemy.name} fought back! You took $dmgTaken damage.")
                if (p.hp <= 0) {
                    inBattle = false
                }
            }

            else if (enemy.hp <= 0) {
                println("Enemy has been defeated!")
                println(gameRepository.updateKillsAndCheckEvolution())
                inBattle = false
            }
        }
    }

}

