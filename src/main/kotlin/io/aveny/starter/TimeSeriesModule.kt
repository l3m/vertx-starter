package io.aveny.starter

import dagger.Module
import dagger.Provides

@Module
object TimeSeriesModule {

  @Provides
  fun provideTimeSeries() : TimeSeries = SimpleTimeSeries()
}
