package at.devfest.app.core.dagger.module

import android.app.Application

import javax.inject.Singleton

import at.devfest.app.DevFestApp
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: DevFestApp) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return app
    }
}
