package com.rcacao.marvelchallenge.data.mapper

import com.rcacao.marvelchallenge.data.model.comics.ComicsDataResponse
import com.rcacao.marvelchallenge.data.model.thumbnail.ThumbnailResponse
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import javax.inject.Inject

class ComicsMapper @Inject constructor() :
    Mapper<ComicsDataResponse, List<ComicsModel>> {

    override fun map(input: ComicsDataResponse): List<ComicsModel> {
        TODO("Not yet implemented")
    }

    private fun getListImageUrl(thumbnail: ThumbnailResponse): String =
        getImageUrl(thumbnail, CharacterMapperImageSize.X_LARGE_SQUARE)

    private fun getDetailImageUrl(thumbnail: ThumbnailResponse): String =
        getImageUrl(thumbnail, CharacterMapperImageSize.AMAZING_LANDSCAPE)

    private fun getImageUrl(thumbnail: ThumbnailResponse, size: CharacterMapperImageSize): String =
        "${thumbnail.path}/${size.path}.${thumbnail.extension}"

    enum class CharacterMapperImageSize(val path: String) {
        X_LARGE_SQUARE("standard_xlarge"),
        AMAZING_LANDSCAPE("landscape_amazing")
    }
}
