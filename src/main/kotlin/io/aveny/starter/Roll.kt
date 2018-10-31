package io.aveny.starter

import kotlin.random.Random

data class Roll(val diceCount: Int, val die: Int, val bonus: Int)

data class RollStats(val min: Int, val max: Int, val expected: Double)

data class RollResult(val roll: Roll, val rollStats: RollStats, val value: Int)

fun makeRollStats(roll: Roll): RollStats {
  val min = roll.diceCount + roll.bonus
  val max = roll.diceCount * roll.die + roll.bonus
  val expected = roll.diceCount + (roll.die / 2) + 0.5 + roll.bonus
  return RollStats(min, max, expected)
}

fun roll(roll: Roll): RollResult {
  val rs = makeRollStats(roll)
  val rolledValue = Random.nextInt(1, roll.die)
  val total = rolledValue + roll.bonus
  return RollResult(roll, rs, total)
}
