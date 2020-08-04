package com.rcacao.marvelchallenge.data.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.rcacao.marvelchallenge.data.CharacterResponse
import com.rcacao.marvelchallenge.data.ThumbnailResponse
import com.rcacao.marvelchallenge.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterMapper @Inject constructor() :
    Mapper<Flow<PagingData<CharacterResponse>>, Flow<PagingData<CharacterModel>>> {
    override fun map(input: Flow<PagingData<CharacterResponse>>): Flow<PagingData<CharacterModel>> {
        return input.map { pagingData: PagingData<CharacterResponse> ->
            pagingData.map {
                CharacterModel(
                    it.id,
                    it.name.trim(),
                    getListImage(it.thumbnail)
                )
            }
        }
    }

    private fun getListImage(thumbnail: ThumbnailResponse): String =
        getImageUrl(thumbnail, CharacterMapperImageSize.MEDIUM_SQUARE)

    private fun getImageUrl(thumbnail: ThumbnailResponse, size: CharacterMapperImageSize): String =
        "${thumbnail.path}/${size.path}.${thumbnail.extension}"


    enum class CharacterMapperImageSize(val path: String) {
        MEDIUM_SQUARE("standard_medium")
    }

}
