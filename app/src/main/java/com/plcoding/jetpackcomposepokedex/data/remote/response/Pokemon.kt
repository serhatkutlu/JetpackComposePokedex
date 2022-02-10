package com.plcoding.jetpackcomposepokedex.data.remote.response


import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("abilities")
    val abilities: List<Ability>,
    @SerializedName("base_experience")
    val baseExperience: Int, // 101
    @SerializedName("forms")
    val forms: List<Form>,
    @SerializedName("game_indices")
    val gameİndices: List<Gameİndice>,
    @SerializedName("height")
    val height: Int, // 3
    @SerializedName("held_items")
    val heldİtems: List<Heldİtem>,
    @SerializedName("id")
    val id: Int, // 132
    @SerializedName("is_default")
    val isDefault: Boolean, // true
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String, // https://pokeapi.co/api/v2/pokemon/132/encounters
    @SerializedName("moves")
    val moves: List<Move>,
    @SerializedName("name")
    val name: String, // ditto
    @SerializedName("order")
    val order: Int, // 203
    @SerializedName("past_types")
    val pastTypes: List<Any>,
    @SerializedName("species")
    val species: Species,
    @SerializedName("sprites")
    val sprites: Sprites,
    @SerializedName("stats")
    val stats: List<Stat>,
    @SerializedName("types")
    val types: List<Type>,
    @SerializedName("weight")
    val weight: Int // 40
)