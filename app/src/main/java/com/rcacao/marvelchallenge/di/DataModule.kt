package com.rcacao.marvelchallenge.di

import androidx.paging.PagingConfig
import com.rcacao.marvelchallenge.BuildConfig
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
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

}