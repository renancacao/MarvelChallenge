package com.rcacao.marvelchallenge.data

import com.google.gson.annotations.SerializedName

class CharacterResponse {

    @SerializedName("id")
    var id: String = ""

    @SerializedName("name")
    val name: String = ""

    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse = ThumbnailResponse()

}
