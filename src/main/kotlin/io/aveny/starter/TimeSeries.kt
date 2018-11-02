package io.aveny.starter

data class MinMax(val min : Double?, val max: Double?, val wait : Long)

interface TimeSeries {

  val size: Int
  val values : DoubleArray

  fun findMinMax() : MinMax
}

