package com.rcacao.marvelchallenge.di

import androidx.paging.PagingData
import com.rcacao.marvelchallenge.data.mapper.CharacterMapper
import com.rcacao.marvelchallenge.data.mapper.ComicsMapper
import com.rcacao.marvelchallenge.data.mapper.Mapper
import com.rcacao.marvelchallenge.data.model.character.CharacterResponse
import com.rcacao.marvelchallenge.data.model.comics.ComicsDataResponse
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object MappersModule {

    @Provides
    @Singleton
    fun providesCharacterMapper(): Mapper<Flow<PagingData<CharacterResponse>>,
            Flow<PagingData<CharacterModel>>> = CharacterMapper()

    @Provides
    @Singleton
    fun providesComicsMapper(): Mapper<ComicsDataResponse, List<ComicsModel>> = ComicsMapper()

}