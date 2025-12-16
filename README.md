# -vaib
import kotlin.random.Random

fun main() {
    val game = AdventureGame()
    game.startGame()
}

class AdventureGame {
    private var playerPosition = 0
    private val targetPosition = 110.6
    private val playerSpeed = 2.0
    private var playerHealth = 100.0
    private var enemiesDefeated = 0
    private var inventory = mutableListOf<String>()

    private var hasSword = false
    private var hasArmor = false
    private var hasPotion = false

    fun startGame() {
        println("=== ИГРА НАЧАЛАСЬ ===")
        println("Цель: достичь позиции $targetPosition")
        println("Стартовая позиция: $playerPosition")
        println("Скорость игрока: $playerSpeed за ход")
        println()

        while (playerPosition < targetPosition && playerHealth > 0) {
            movePlayer()
            if (playerHealth <= 0) {
                println("Игрок погиб! Игра окончена.")
                return
            }
        }

        if (playerPosition >= targetPosition) {
            println("\n=== ПОБЕДА! ===")
            println("Игрок достиг цели на позиции $playerPosition")
            println("Побеждено врагов: $enemiesDefeated")
            println("Инвентарь: ${inventory.joinToString(", ")}")
        }
    }

    private fun movePlayer() {
        println("\nТекущая позиция: $playerPosition")

        // Шанс встретить предмет (30%)
        if (Random.nextDouble() < 0.3) {
            findItem()
        }

        // Шанс встретить врага (случайное количество от 8 до 100.6)
        val enemyChance = Random.nextDouble(8.0, 100.6)
        if (Random.nextDouble() * 100 < enemyChance) {
            encounterEnemy()
        }

        // Если машина предает (60% шанс)
        if (Random.nextDouble() < 0.6) {
            println("Машина предает! Возможности ограничены.")
            // Здесь можно добавить эффекты от предательства машины
        }
    }

    private fun findItem() {
        val itemType = Random.nextInt(1, 4)
        when (itemType) {
            1 -> {
                hasSword = true
                inventory.add("Меч")
                println("Найден МЕЧ! Урон увеличен.")
            }
            2 -> {
                hasArmor = true
                inventory.add("Броня")
                println("Найдена БРОНЯ! Защита увеличена.")
            }
            3 -> {
                hasPotion = true
                inventory.add("Зелье")
                println("Найдено ЗЕЛЬЕ! Можно восстановить здоровье.")
            }
        }
    }

    private fun encounterEnemy() {
        println("\n⚔️ ВСТРЕЧЕН ВРАГ!")

        val enemyHealth = Random.nextDouble(40.0, 90.0)
        var currentEnemyHealth = enemyHealth
        val enemyDamageRange = 5..20

        println("Здоровье врага: ${"%.1f".format(enemyHealth)}")
        println("Ваше здоровье: ${"%.1f".format(playerHealth)}")

        while (currentEnemyHealth > 0 && playerHealth > 0) {
            // Урон игрока (10-40)
            val playerDamage = Random.nextDouble(10.0, 40.0)
            val modifiedPlayerDamage = if (hasSword) playerDamage * 1.5 else playerDamage
            currentEnemyHealth -= modifiedPlayerDamage

            println("Вы наносите ${"%.1f".format(modifiedPlayerDamage)} урона врагу")

            if (currentEnemyHealth <= 0) {
                println("Враг побежден!")
                enemiesDefeated++

                // Шанс получить предмет от врага (60%)
                if (Random.nextDouble() < 0.6 && !hasPotion) {
                    hasPotion = true
                    inventory.add("Зелье от врага")
                    println("Враг выронил ЗЕЛЬЕ!")
                }
                return
            }

            // Урон врага (5-20)
            val enemyDamage = Random.nextDouble(5.0, 20.0)
            val modifiedEnemyDamage = if (hasArmor) enemyDamage * 0.7 else enemyDamage
            playerHealth -= modifiedEnemyDamage

            println("Враг наносит ${"%.1f".format(modifiedEnemyDamage)} урона вам")
            println("Остаток здоровья врага: ${"%.1f".format(currentEnemyHealth)}")
            println("Ваше здоровье: ${"%.1f".format(playerHealth)}")

            // Использование зелья если здоровье мало
            if (playerHealth < 30 && hasPotion) {
                usePotion()
            }
        }

        if (playerHealth <= 0) {
            println("Вы проиграли врагу...")
        }
    }

    private fun usePotion() {
        if (hasPotion) {
            val healAmount = Random.nextDouble(20.0, 50.0)
            playerHealth += healAmount
            hasPotion = false
            inventory.remove("Зелье")
            inventory.remove("Зелье от врага")
            println("Использовано ЗЕЛЬЕ! +${"%.1f".format(healAmount)} здоровья")
        }
    }
}

