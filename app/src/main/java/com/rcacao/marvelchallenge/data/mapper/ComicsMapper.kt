package com.rcacao.marvelchallenge.data.mapper

import com.rcacao.marvelchallenge.data.model.comics.ComicsDataResponse
import com.rcacao.marvelchallenge.data.model.thumbnail.ThumbnailResponse
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import javax.inject.Inject

class ComicsMapper @Inject constructor() :
    Mapper<ComicsDataResponse, List<ComicsModel>> {

    override fun map(input: ComicsDataResponse): List<ComicsModel> {
        return input.data?.comics?.map { ComicsModel(getImageListUrl(it.thumbnail), it.title) }
            ?: emptyList()
    }

    private fun getImageListUrl(thumbnail: ThumbnailResponse): String =
        getImageUrl(thumbnail, ComicsMapperImageSize.X_LARGE_PORTRAIT)

    private fun getImageUrl(thumbnail: ThumbnailResponse, size: ComicsMapperImageSize): String =
        "${thumbnail.path}/${size.path}.${thumbnail.extension}"

    enum class ComicsMapperImageSize(val path: String) {
        X_LARGE_PORTRAIT("portrait_xlarge"),
    }
}
