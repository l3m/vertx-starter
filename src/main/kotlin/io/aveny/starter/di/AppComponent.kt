package io.aveny.starter.di

import dagger.Component
import io.aveny.starter.MainVerticle

@Component(modules = [TimeSeriesModule::class])
interface AppComponent {
  fun inject(mv : MainVerticle)
}
