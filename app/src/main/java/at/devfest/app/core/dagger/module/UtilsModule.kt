package at.devfest.app.core.dagger.module

import android.app.Application
import at.devfest.app.core.firebase.FirebaseAnalyticsWrapper
import at.devfest.app.core.firebase.FirebaseConfiguration
import at.devfest.app.utils.Analytics
import at.devfest.app.utils.Configuration
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by helmuth on 19/08/16.
 */
@Module
class UtilsModule {

    @Provides
    @Singleton
    internal fun provideConfiguration(): Configuration {
        return FirebaseConfiguration(
                FirebaseRemoteConfig.getInstance())
    }

    @Provides
    @Singleton
    internal fun provideAnalytics(context: Application): Analytics {
        return FirebaseAnalyticsWrapper(
                FirebaseAnalytics.getInstance(context)
        )
    }

    @Provides
    @Singleton
    internal fun provideDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    @Provides
    @Singleton
    internal fun provideFirebaseInstanceId(): FirebaseInstanceId {
        return FirebaseInstanceId.getInstance()
    }
}
