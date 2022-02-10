package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class MoveX(
    @SerializedName("name")
    val name: String, // transform
    @SerializedName("url")
    val url: String // https://pokeapi.co/api/v2/move/144/
)