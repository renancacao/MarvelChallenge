package com.rcacao.marvelchallenge.di

import com.rcacao.marvelchallenge.data.api.MarvelWebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object ServiceModule {

    @Provides
    fun provideMarvelService(): MarvelWebService = MarvelWebService.create()

}