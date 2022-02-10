package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class StatX(
    @SerializedName("name")
    val name: String, // hp
    @SerializedName("url")
    val url: String // https://pokeapi.co/api/v2/stat/1/
)