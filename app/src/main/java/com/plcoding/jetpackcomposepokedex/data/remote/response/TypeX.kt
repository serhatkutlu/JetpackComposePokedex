package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class TypeX(
    @SerializedName("name")
    val name: String, // normal
    @SerializedName("url")
    val url: String // https://pokeapi.co/api/v2/type/1/
)