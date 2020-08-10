package com.rcacao.marvelchallenge.di

import androidx.paging.PagingData
import com.rcacao.marvelchallenge.data.database.Character
import com.rcacao.marvelchallenge.data.mapper.*
import com.rcacao.marvelchallenge.data.model.character.CharacterResponse
import com.rcacao.marvelchallenge.data.model.comics.ComicsDataResponse
import com.rcacao.marvelchallenge.data.model.series.SeriesDataResponse
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel
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
    fun providesPagingCharacterModelMapper(): Merger<Flow<PagingData<CharacterResponse>>, String,
            Flow<PagingData<CharacterModel>>> = PagingCharacterModelMerger()

    @Provides
    @Singleton
    fun providesComicsMapper(): Mapper<ComicsDataResponse, List<ComicsModel>> = ComicsMapper()

    @Provides
    @Singleton
    fun providesSeriesMapper(): Mapper<SeriesDataResponse, List<SeriesModel>> = SeriesMapper()

    @Provides
    @Singleton
    fun providesCharacterMapper(): Mapper<CharacterModel, Character> = CharacterMapper()

    @Provides
    @Singleton
    fun providesCharacterModelMapper(): Mapper<List<Character>, ArrayList<CharacterModel>> =
        ListCharacterModelMapper()


}
