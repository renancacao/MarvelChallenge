package com.rcacao.marvelchallenge.di

import com.rcacao.marvelchallenge.data.api.MarvelWebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesMarvelService(): MarvelWebService = MarvelWebService.create()

}