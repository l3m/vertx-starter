package io.aveny.starter

import kotlin.random.Random

data class MinMax(val min : Double?, val max: Double?)

class TimeSeries(val size : Int = 100_000) {
  val values : DoubleArray = DoubleArray(size)

  init {
    randomize()
  }

  fun randomize() {
    for (x in 0 until size) {
      values[x] = Random.nextDouble(from = 0.0, until = 1e8)
    }
  }

  fun findMinMax() : MinMax {
    return MinMax(
      min = values.min(),
      max = values.max()
    )
  }
}
