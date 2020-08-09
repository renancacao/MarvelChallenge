package com.rcacao.marvelchallenge.di

import androidx.paging.PagingData
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel
import com.rcacao.marvelchallenge.domain.usecases.*
import com.rcacao.marvelchallenge.domain.usecases.GetCharactersPagingUseCase.CharacterPagingRequest
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Qualifier

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class UseCasesModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SaveUseCase

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DeleteUseCase

    @DeleteUseCase
    @Binds
    abstract fun bindDeleteFavoriteUseCase(useCase: DeleteFavoriteUseCase): UseCase<CharacterModel, DataResult<Unit>>

    @SaveUseCase
    @Binds
    abstract fun bindSaveFavoriteUseCase(useCase: SaveFavoriteUseCase): UseCase<CharacterModel, DataResult<Unit>>

    @Binds
    abstract fun bindGetCharacterPagingUseCase(useCase: GetCharactersPagingUseCase):
            UseCase<CharacterPagingRequest, Flow<PagingData<CharacterModel>>>

    @Binds
    abstract fun bindGetComicsUseCase(useCase: GetComicsUseCase): UseCase<String, DataResult<List<ComicsModel>>>

    @Binds
    abstract fun bindGetSeriesUseCase(useCase: GetSeriesUseCase): UseCase<String, DataResult<List<SeriesModel>>>

    @Binds
    abstract fun bindGetFavoritesUseCase(useCase: GetFavoritesUseCase): UseCase<String, DataResult<List<CharacterModel>>>
}

