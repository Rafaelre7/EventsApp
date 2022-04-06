package com.example.eventosapp.di

import android.content.Context
import com.example.eventosapp.BuildConfig
import com.example.eventosapp.data.remote.EventRemoteDataSource
import com.example.eventosapp.data.remote.EventService
import com.example.eventosapp.data.repository.CheckInRepository
import com.example.eventosapp.data.repository.EventRepository
import com.example.eventosapp.data.local.AppDatabase
import com.example.eventosapp.data.local.EventDao
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideEventService(retrofit: Retrofit): EventService =
        retrofit.create(EventService::class.java)

    @Singleton
    @Provides
    fun provideEventRemoteDataSource(eventService: EventService) =
        EventRemoteDataSource(eventService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideEventDao(db: AppDatabase) = db.eventDao()

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: EventRemoteDataSource,
        localDataSource: EventDao
    ) =
        EventRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideCheckinRepository(remoteDataSource: EventRemoteDataSource) =
        CheckInRepository(remoteDataSource)

}