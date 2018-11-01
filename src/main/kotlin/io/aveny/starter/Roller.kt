package io.aveny.starter

data class RollDesc(val diceCount: Int, val die: Int, val bonus: Int)

data class RollStats(val min: Int, val max: Int, val expected: Double)

data class RollResult(val desc: RollDesc, val stats: RollStats, val result: Int)

interface Roller {
  fun roll(rollDesc: RollDesc): RollResult
}
