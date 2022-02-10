package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("back_default")
    val backDefault: String, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png
    @SerializedName("back_female")
    val backFemale: Any, // null
    @SerializedName("back_shiny")
    val backShiny: String, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/132.png
    @SerializedName("back_shiny_female")
    val backShinyFemale: Any, // null
    @SerializedName("front_default")
    val frontDefault: String, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png
    @SerializedName("front_female")
    val frontFemale: Any, // null
    @SerializedName("front_shiny")
    val frontShiny: String, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png
    @SerializedName("front_shiny_female")
    val frontShinyFemale: Any, // null
    @SerializedName("other")
    val other: Other,
    @SerializedName("versions")
    val versions: Versions
)