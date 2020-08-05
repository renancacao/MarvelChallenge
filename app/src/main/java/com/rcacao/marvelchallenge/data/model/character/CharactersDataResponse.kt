package com.rcacao.marvelchallenge.data.model.character

import com.google.gson.annotations.SerializedName

class CharactersDataResponse {

    @SerializedName("data")
    var data: CharactersListResponse? = null

}
