package io.aveny.starter

import dagger.Component

@Component(modules = [TimeSeriesModule::class])
interface AppComponent {
  fun inject(mv : MainVerticle)
}
