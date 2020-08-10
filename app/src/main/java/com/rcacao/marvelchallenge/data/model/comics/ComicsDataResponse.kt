package com.rcacao.marvelchallenge.data.model.comics

import com.google.gson.annotations.SerializedName

class ComicsDataResponse {

    @SerializedName("data")
    var data: ComicsListResponse? = null

}
