package io.aveny.starter.di

import dagger.Module
import dagger.Provides
import io.aveny.starter.TimeSeries
import io.aveny.starter.details.FakeSlowTimeSeries

@Module
object TimeSeriesModule {

  @Provides
  fun provideTimeSeries() : TimeSeries = FakeSlowTimeSeries()
}
