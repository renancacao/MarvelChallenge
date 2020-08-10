package com.rcacao.marvelchallenge.di

import com.rcacao.marvelchallenge.data.repository.characters.CharactersRepository
import com.rcacao.marvelchallenge.data.repository.characters.CharactersRepositoryImpl
import com.rcacao.marvelchallenge.data.repository.comics.ComicsRepository
import com.rcacao.marvelchallenge.data.repository.comics.ComicsRepositoryImpl
import com.rcacao.marvelchallenge.data.repository.series.SeriesRepository
import com.rcacao.marvelchallenge.data.repository.series.SeriesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCharactersRepository(charactersRepositoryImpl: CharactersRepositoryImpl): CharactersRepository

    @Singleton
    @Binds
    abstract fun bindComicsRepository(comicsRepositoryImpl: ComicsRepositoryImpl): ComicsRepository

    @Singleton
    @Binds
    abstract fun bindSeriesRepository(seriesRepositoryImpl: SeriesRepositoryImpl): SeriesRepository

}
