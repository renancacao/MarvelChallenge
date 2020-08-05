package com.rcacao.marvelchallenge.data.model.comics

import com.google.gson.annotations.SerializedName

class ComicsListResponse {

    @SerializedName("results")
    val comics: List<ComicsResponse> = emptyList()
}
