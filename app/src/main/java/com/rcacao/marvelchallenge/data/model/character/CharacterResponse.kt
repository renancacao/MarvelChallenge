package com.rcacao.marvelchallenge.data.model.character

import com.google.gson.annotations.SerializedName
import com.rcacao.marvelchallenge.data.model.thumbnail.ThumbnailResponse

class CharacterResponse {

    @SerializedName("id")
    var id: String = ""

    @SerializedName("name")
    val name: String = ""

    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse =
        ThumbnailResponse()

    @SerializedName("description")
    val description: String = ""

}
