package io.aveny.starter

import kotlin.random.Random

fun makeRollStats(rollDesc: RollDesc): RollStats {
  val min = rollDesc.diceCount + rollDesc.bonus
  val max = rollDesc.diceCount * rollDesc.die + rollDesc.bonus
  val expected = rollDesc.diceCount + (rollDesc.die / 2) + 0.5 + rollDesc.bonus
  return RollStats(min, max, expected)
}

class DefaultRoller : Roller {

  override fun roll(rollDesc: RollDesc): RollResult {
    val rs = makeRollStats(rollDesc)
    val rolledValue = Random.nextInt(1, rollDesc.die)
    val total = rolledValue + rollDesc.bonus
    return RollResult(rollDesc, rs, total)
  }

}
