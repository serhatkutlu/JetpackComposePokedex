package com.plcoding.jetpackcomposepokedex.Repository

import com.plcoding.jetpackcomposepokedex.Util.Resource
import com.plcoding.jetpackcomposepokedex.data.remote.PokeApi
import com.plcoding.jetpackcomposepokedex.data.remote.response.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.response.PokemonList
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api:PokeApi
){

    suspend fun getPokemonListRepo(limit:Int,offset:Int):Resource<PokemonList>{
        val responce=try {
            api.getPokemonList(limit = limit, offset = offset)
        }catch (e:Exception){
            return Resource.Error(message = "Unknown Error")
        }
        return Resource.Success(responce)
    }

    suspend fun getPokemonRepo(name:String):Resource<Pokemon>{
        val responce=try {
            api.getPokemonInfo(name)
        }catch (e:Exception){
            return Resource.Error(message = "Unknown Error")
        }
        return Resource.Success(responce)
    }
}