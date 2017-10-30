package at.devfest.app.core.dagger.module

import android.app.Application
import android.content.SharedPreferences
import android.support.v7.preference.PreferenceManager
import at.devfest.app.utils.LocalDateTimeAdapter
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    internal fun provideSharedPreferences(app: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(app: Application): OkHttpClient {
        val cacheDir = File(app.cacheDir, "http")
        val cache = Cache(cacheDir, DISK_CACHE_SIZE)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        return builder.addInterceptor(interceptor).cache(cache).build()
    }

    @Provides
    @Singleton
    internal fun providePicasso(app: Application, client: OkHttpClient): Picasso {
        return Picasso.Builder(app)
                .downloader(OkHttp3Downloader(client))
                .listener { picasso, uri, e -> Timber.e(e, "Failed to load image: %s", uri) }
                .build()
    }

    companion object {

        private val DISK_CACHE_SIZE: Long = 31_457_280 // 30MB
    }
}
