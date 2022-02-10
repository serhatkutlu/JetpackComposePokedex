package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class MoveLearnMethod(
    @SerializedName("name")
    val name: String, // level-up
    @SerializedName("url")
    val url: String // https://pokeapi.co/api/v2/move-learn-method/1/
)