package com.rcacao.marvelchallenge.di

import android.content.Context
import androidx.paging.PagingConfig
import androidx.room.Room
import com.rcacao.marvelchallenge.BuildConfig
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesMarvelService(): MarvelWebService = MarvelWebService.create()

    @Provides
    @Singleton
    fun providesPagingConfig(): PagingConfig = PagingConfig(
        pageSize = BuildConfig.PAGE_SIZE,
        enablePlaceholders = false,
        initialLoadSize = BuildConfig.PAGE_SIZE
    )

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase =
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "marvel-db"
        ).build()

}