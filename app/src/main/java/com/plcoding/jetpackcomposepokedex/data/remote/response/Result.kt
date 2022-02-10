package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("name")
    val name: String, // bulbasaur
    @SerializedName("url")
    val url: String // https://pokeapi.co/api/v2/pokemon/1/
)