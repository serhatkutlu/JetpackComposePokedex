package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class VersionX(
    @SerializedName("name")
    val name: String, // ruby
    @SerializedName("url")
    val url: String // https://pokeapi.co/api/v2/version/7/
)