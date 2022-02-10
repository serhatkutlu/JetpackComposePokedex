package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("base_stat")
    val baseStat: Int, // 48
    @SerializedName("effort")
    val effort: Int, // 1
    @SerializedName("stat")
    val stat: StatX
)