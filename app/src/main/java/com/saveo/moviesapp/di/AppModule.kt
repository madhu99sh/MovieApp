package com.saveo.moviesapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.saveo.moviesapp.data.HeaderInterceptor
import com.saveo.moviesapp.data.helper.MovieApiHelper
import com.saveo.moviesapp.data.helper.MovieApiHelperImpl
import com.saveo.moviesapp.data.service.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideMoviesApiService(
        gson: Gson,
        headerInterceptor: HeaderInterceptor
    ): MovieApiService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(logging)
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES).build()
        val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl("https://devru-bigflix-movies-download-v1.p.rapidapi.com/")
            .client(client)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
        val retrofit = retrofitBuilder.build()
        return retrofit.create(MovieApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideHeaderInterceptor() = HeaderInterceptor()

    @Provides
    @Singleton
    fun provideMovieApiHelper(apiHelper: MovieApiHelperImpl): MovieApiHelper = apiHelper


}