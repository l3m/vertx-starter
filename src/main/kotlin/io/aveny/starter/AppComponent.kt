package io.aveny.starter

import dagger.Component

@Component(modules = [RollerModule::class])
interface AppComponent {
  fun inject(mv : MainVerticle)
}
