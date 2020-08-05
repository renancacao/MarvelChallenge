package com.rcacao.marvelchallenge.di

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rcacao.marvelchallenge.BuildConfig
import com.rcacao.marvelchallenge.data.model.character.CharacterResponse
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.mapper.CharacterMapper
import com.rcacao.marvelchallenge.data.mapper.Mapper
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.flow.Flow
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
    fun providesCharacterMapper():
            Mapper<Flow<PagingData<CharacterResponse>>, Flow<PagingData<CharacterModel>>> =
        CharacterMapper()

}