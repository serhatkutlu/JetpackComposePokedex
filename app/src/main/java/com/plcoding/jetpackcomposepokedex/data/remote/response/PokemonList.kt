package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class PokemonList(
    @SerializedName("count")
    val count: Int, // 1118
    @SerializedName("next")
    val next: String, // https://pokeapi.co/api/v2/pokemon?offset=20&limit=20
    @SerializedName("previous")
    val previous: Any, // null
    @SerializedName("results")
    val results: List<Result>
)