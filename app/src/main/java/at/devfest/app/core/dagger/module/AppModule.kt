package at.devfest.app.core.dagger.module

import android.app.Application
import at.devfest.app.DevFestApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: DevFestApp) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return app
    }
}
