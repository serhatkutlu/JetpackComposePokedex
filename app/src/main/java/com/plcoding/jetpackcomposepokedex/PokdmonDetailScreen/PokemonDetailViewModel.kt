package com.plcoding.jetpackcomposepokedex.PokdmonDetailScreen

import androidx.lifecycle.ViewModel
import com.plcoding.jetpackcomposepokedex.Repository.PokemonRepository
import com.plcoding.jetpackcomposepokedex.Util.Resource
import com.plcoding.jetpackcomposepokedex.data.remote.response.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    val repository:PokemonRepository
):ViewModel(){

    suspend fun getPokemonInfo(name:String):Resource<Pokemon>{

        return repository.getPokemonRepo(name)

    }
}
