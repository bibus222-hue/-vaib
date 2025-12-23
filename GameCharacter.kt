package lesson1000

import kotlinx.coroutines.*

class GameCharacter(
    var name: String,
    var maxHealth: Int,
    var baseAttack: Int,
    var maxMana: Int = 100
) {
    var currentHealth = maxHealth
    var currentMana = maxMana
    var attackBonus = 0  // Новое поле для бонуса атаки

    fun isAlive(): Boolean {
        return currentHealth > 0
    }

    fun attack(target: GameCharacter): Int {
        if (!isAlive() || !target.isAlive()) return 0

        val damage = baseAttack + attackBonus
        target.takeDamage(damage)
        return damage
    }

    fun takeDamage(damage: Int) {
        currentHealth -= damage
        if (currentHealth < 0) currentHealth = 0
    }

    fun applyAttackBuff(bonus: Int, durationMillis: Long) {
        if (!isAlive()) {
            println("$name мёртв, бафф не может быть применён.")
            return
        }

        GlobalScope.launch {
            println("$name получает бафф силы +$bonus на ${durationMillis}мс")
            attackBonus += bonus

            delay(durationMillis)

            attackBonus -= bonus
            println("Бафф силы на $name закончился.")
        }
    }

    // Существующие эффекты:
    fun applyPoisonEffect(damagePerTick: Int, ticks: Int, intervalMillis: Long) {
        if (!isAlive()) {
            println("$name мёртв, яд не может быть применён.")
            return
        }

        GlobalScope.launch {
            repeat(ticks) {
                if (isAlive()) {
                    takeDamage(damagePerTick)
                    println("$name отравлен, здоровье: $currentHealth")
                    delay(intervalMillis)
                } else {
                    println("$name умер, эффект яда прекращается.")
                    return@launch
                }
            }
        }
    }

    fun startManaRegeneration(intervalMillis: Long, amountPerTick: Int) {
        if (!isAlive()) {
            println("$name мёртв, регенерация маны невозможна.")
            return
        }

        GlobalScope.launch {
            while (isAlive()) {
                delay(intervalMillis)
                currentMana = (currentMana + amountPerTick).coerceAtMost(maxMana)
                println("$name восстанавливает ману: $currentMana/$maxMana")
            }
            println("Регенерация маны для $name прекращена.")
        }
    }
}
