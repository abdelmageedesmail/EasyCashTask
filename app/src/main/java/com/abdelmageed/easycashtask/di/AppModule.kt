package com.abdelmageed.islamicapp.di

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import com.abdelmageed.easycashtask.BuildConfig
import com.abdelmageed.easycashtask.MyApplication
import com.abdelmageed.easycashtask.data.locale.db.CompetitionsDao
import com.abdelmageed.easycashtask.data.locale.db.RoomDataBase
import com.abdelmageed.easycashtask.data.remote.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

    @Provides
    @Singleton
    fun addHttpClient(): OkHttpClient.Builder {
        val addInterceptor = httpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val request = original.newBuilder()
                    .header("X-Auth-Token", BuildConfig.X_Auth_Token)
                    .method(original.method, original.body)
                    .build()
                return chain.proceed(request)
            }

        })
        return addInterceptor
    }

    @Provides
    @Singleton
    fun initApiService(): ApiInterface {
        val client = addHttpClient().build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiInterface::class.java)
    }


    @Singleton
    @Provides
    fun initCatRoomDataBase(): RoomDataBase {
        return RoomDataBase.getDatabase(application)
    }

    @Provides
    @Singleton
    fun initDao(): CompetitionsDao {
        return initCatRoomDataBase().competitionsDao()
    }

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application

}