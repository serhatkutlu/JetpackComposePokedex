package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class AbilityX(
    @SerializedName("name")
    val name: String, // limber
    @SerializedName("url")
    val url: String // https://pokeapi.co/api/v2/ability/7/
)