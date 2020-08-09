package com.rcacao.marvelchallenge.data.model.thumbnail

import com.google.gson.annotations.SerializedName

class ThumbnailResponse {

    @SerializedName("path")
    var path: String = ""

    @SerializedName("extension")
    var extension: String = ""

}
