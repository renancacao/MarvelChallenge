package com.rcacao.marvelchallenge.data.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.rcacao.marvelchallenge.data.model.character.CharacterResponse
import com.rcacao.marvelchallenge.data.model.thumbnail.ThumbnailResponse
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PagingCharacterModelMerger @Inject constructor() :
    Merger<Flow<PagingData<CharacterResponse>>, String, Flow<PagingData<CharacterModel>>> {

    override fun mapAndMerge(
        input: Flow<PagingData<CharacterResponse>>,
        list: List<String>
    ): Flow<PagingData<CharacterModel>> {
        return input.map { pagingData: PagingData<CharacterResponse> ->
            pagingData.map {
                CharacterModel(
                    it.id,
                    it.name.trim(),
                    getImageListUrl(it.thumbnail),
                    getImageDetailUrl(it.thumbnail),
                    it.description,
                    it.id in list
                )
            }
        }
    }

    override fun map(input: Flow<PagingData<CharacterResponse>>): Flow<PagingData<CharacterModel>> {
        return input.map { pagingData: PagingData<CharacterResponse> ->
            pagingData.map {
                CharacterModel(
                    it.id,
                    it.name.trim(),
                    getImageListUrl(it.thumbnail),
                    getImageDetailUrl(it.thumbnail),
                    it.description
                )
            }
        }
    }

    private fun getImageListUrl(thumbnail: ThumbnailResponse): String =
        getImageUrl(thumbnail, CharacterMapperImageSize.X_LARGE_SQUARE)

    private fun getImageDetailUrl(thumbnail: ThumbnailResponse): String =
        getImageUrl(thumbnail, CharacterMapperImageSize.AMAZING_LANDSCAPE)

    private fun getImageUrl(thumbnail: ThumbnailResponse, size: CharacterMapperImageSize): String =
        "${thumbnail.path}/${size.path}.${thumbnail.extension}"

    enum class CharacterMapperImageSize(val path: String) {
        X_LARGE_SQUARE("standard_xlarge"),
        AMAZING_LANDSCAPE("landscape_amazing")
    }


}
