package lesson52

import kotlinx.coroutines.*
import lesson1000.GameCharacter

fun main() = runBlocking {
    val player = GameCharacter("Олег", 100, 10)
    val enemy = GameCharacter("Гоблин", 50, 5)

    println("Олег получает бафф силы +5 на 5 секунд...")
    player.applyAttackBuff(5, 5000L)

    // Дадим время начаться корутине баффа
    delay(100)

    // Атака с баффом
    println("Атака с баффом:")
    player.attack(enemy)
    println("Здоровье гоблина: ${enemy.currentHealth}")

    // Ждём окончания баффа
    delay(6000)

    // Атака без баффа
    println("Атака без баффа:")
    player.attack(enemy)
    println("Здоровье гоблина: ${enemy.currentHealth}")

    // Пример с ядом после смерти
    enemy.applyPoisonEffect(10, 3, 1000L)
    delay(5000)
}
