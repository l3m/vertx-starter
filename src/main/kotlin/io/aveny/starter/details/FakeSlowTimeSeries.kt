package io.aveny.starter.details

import io.aveny.starter.MinMax
import io.aveny.starter.TimeSeries
import kotlin.random.Random

class FakeSlowTimeSeries(override val size : Int = 100_000) : TimeSeries {
  override val values : DoubleArray = DoubleArray(size)

  init {
    randomize()
  }

  fun randomize() {
    for (x in 0 until size) {
      values[x] = Random.nextDouble(from = 0.0, until = 1e8)
    }
  }

  override fun findMinMax() : MinMax {
    val wait = Random.nextLong(from = 1, until = 10_000)
    Thread.sleep(wait)
    return MinMax(
      min = values.min(),
      max = values.max(),
      wait = wait
    )
  }
}
