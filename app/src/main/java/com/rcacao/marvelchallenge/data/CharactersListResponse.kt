package com.rcacao.marvelchallenge.data

import com.google.gson.annotations.SerializedName

class CharactersListResponse {

    @SerializedName("total")
    var total: Int = 0

    @SerializedName("results")
    var characters: List<CharacterResponse> = emptyList()

    @SerializedName("code")
    var testCode: Int = 0

}
