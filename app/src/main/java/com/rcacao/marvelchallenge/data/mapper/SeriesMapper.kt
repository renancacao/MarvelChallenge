package com.rcacao.marvelchallenge.data.mapper

import com.rcacao.marvelchallenge.data.model.series.SeriesDataResponse
import com.rcacao.marvelchallenge.data.model.thumbnail.ThumbnailResponse
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel
import javax.inject.Inject

class SeriesMapper @Inject constructor() :
    Mapper<SeriesDataResponse, List<SeriesModel>> {

    override fun map(input: SeriesDataResponse): List<SeriesModel> {
        return input.data?.series?.map { SeriesModel(getImageListUrl(it.thumbnail), it.title) }
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
