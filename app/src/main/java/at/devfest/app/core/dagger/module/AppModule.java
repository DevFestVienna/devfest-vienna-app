package at.devfest.app.core.dagger.module;

import android.app.Application;

import javax.inject.Singleton;

import at.devfest.app.DevFestApp;
import dagger.Module;
import dagger.Provides;

@Module
public final class AppModule {

    private final DevFestApp app;

    public AppModule(DevFestApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }
}
