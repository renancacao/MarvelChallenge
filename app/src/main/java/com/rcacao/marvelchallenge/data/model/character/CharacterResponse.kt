package com.rcacao.marvelchallenge.data.model.character

import com.google.gson.annotations.SerializedName
import com.rcacao.marvelchallenge.data.model.thumbnail.ThumbnailResponse

class CharacterResponse {

    @SerializedName("id")
    var id: String = ""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("thumbnail")
    var thumbnail: ThumbnailResponse =
        ThumbnailResponse()

    @SerializedName("description")
    var description: String = ""

}
