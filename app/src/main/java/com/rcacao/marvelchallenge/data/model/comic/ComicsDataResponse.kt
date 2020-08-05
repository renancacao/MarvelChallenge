package com.rcacao.marvelchallenge.data.model.comic

import com.google.gson.annotations.SerializedName

class ComicsDataResponse {

    @SerializedName("data")
    var data: ComicsListResponse? = null

}
