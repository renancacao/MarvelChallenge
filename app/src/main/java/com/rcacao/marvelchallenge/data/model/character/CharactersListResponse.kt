package com.rcacao.marvelchallenge.data.model.character

import com.google.gson.annotations.SerializedName
import com.rcacao.marvelchallenge.data.model.character.CharacterResponse

class CharactersListResponse {

    @SerializedName("total")
    var total: Int = 0

    @SerializedName("results")
    var characters: List<CharacterResponse> = emptyList()

    @SerializedName("code")
    var testCode: Int = 0

}
