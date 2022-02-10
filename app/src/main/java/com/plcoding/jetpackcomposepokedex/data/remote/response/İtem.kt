package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class İtem(
    @SerializedName("name")
    val name: String, // metal-powder
    @SerializedName("url")
    val url: String // https://pokeapi.co/api/v2/item/234/
)