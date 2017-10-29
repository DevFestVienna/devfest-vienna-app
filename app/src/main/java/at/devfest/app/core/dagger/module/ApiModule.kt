package at.devfest.app.core.dagger.module

import com.squareup.moshi.Moshi

import javax.inject.Singleton

import at.devfest.app.BuildConfig
import at.devfest.app.data.network.DevFestService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    internal fun provideDevFestService(retrofit: Retrofit): DevFestService {
        return retrofit.create(DevFestService::class.java)
    }
}
