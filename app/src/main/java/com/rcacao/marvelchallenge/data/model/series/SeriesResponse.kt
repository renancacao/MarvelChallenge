package com.rcacao.marvelchallenge.data.model.series

import com.google.gson.annotations.SerializedName
import com.rcacao.marvelchallenge.data.model.thumbnail.ThumbnailResponse

class SeriesResponse {

    @SerializedName("id")
    var id: String = ""

    @SerializedName("title")
    val title: String = ""

    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse = ThumbnailResponse()

}
