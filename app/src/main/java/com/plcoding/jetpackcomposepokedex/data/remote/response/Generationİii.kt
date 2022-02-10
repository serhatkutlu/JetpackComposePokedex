package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class Generationİii(
    @SerializedName("emerald")
    val emerald: Emerald,
    @SerializedName("firered-leafgreen")
    val fireredLeafgreen: FireredLeafgreen,
    @SerializedName("ruby-sapphire")
    val rubySapphire: RubySapphire
)