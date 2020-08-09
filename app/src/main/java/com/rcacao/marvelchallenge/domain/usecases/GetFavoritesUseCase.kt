package com.rcacao.marvelchallenge.domain.usecases

import com.rcacao.marvelchallenge.data.repository.characters.CharactersRepository
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: CharactersRepository) :
    UseCase<String, DataResult<@JvmSuppressWildcards List<CharacterModel>>> {
    override suspend operator fun invoke(requestData: String): DataResult<ArrayList<CharacterModel>> =
        repository.getFavorites(requestData)
}
