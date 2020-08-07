package com.rcacao.marvelchallenge.domain.usecases

import com.rcacao.marvelchallenge.data.repository.characters.CharactersRepository
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import javax.inject.Inject


class SaveFavoriteUseCase @Inject constructor(private val repository: CharactersRepository) :
    UseCase<CharacterModel, DataResult<@JvmSuppressWildcards Unit>> {

    override suspend operator fun invoke(requestData: CharacterModel) =
        repository.saveFavorite(requestData)

}