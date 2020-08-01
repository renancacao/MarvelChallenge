package com.rcacao.marvelchallenge.di

import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.datasource.CharactersDataSource
import com.rcacao.marvelchallenge.data.datasource.CharactersDataSourceFactory
import com.rcacao.marvelchallenge.utils.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesMarvelService(): MarvelWebService = MarvelWebService.create()

}