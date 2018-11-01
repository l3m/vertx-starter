package io.aveny.starter

import dagger.Module
import dagger.Provides

@Module
object RollerModule {

  @Provides
  fun provideRoller() : Roller = DefaultRoller()
}
