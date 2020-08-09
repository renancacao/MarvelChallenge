package com.rcacao.marvelchallenge.data.model.character

import com.google.gson.annotations.SerializedName

class CharactersListResponse {

    @SerializedName("results")
    var characters: List<CharacterResponse> = emptyList()

}
