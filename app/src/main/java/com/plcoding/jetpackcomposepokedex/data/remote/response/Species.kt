package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class Species(
    @SerializedName("name")
    val name: String, // ditto
    @SerializedName("url")
    val url: String // https://pokeapi.co/api/v2/pokemon-species/132/
)