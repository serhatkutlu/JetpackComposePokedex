package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class VersionGroup(
    @SerializedName("name")
    val name: String, // red-blue
    @SerializedName("url")
    val url: String // https://pokeapi.co/api/v2/version-group/1/
)