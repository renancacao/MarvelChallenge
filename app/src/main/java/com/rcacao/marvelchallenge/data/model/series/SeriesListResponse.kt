package com.rcacao.marvelchallenge.data.model.series

import com.google.gson.annotations.SerializedName

class SeriesListResponse {

    @SerializedName("results")
    var series: List<SeriesResponse> = emptyList()
}
