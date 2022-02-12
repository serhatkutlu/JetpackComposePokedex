package com.plcoding.jetpackcomposepokedex.PokemonlistScreen

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.graphics.Bitmap

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.provider.DocumentsContract
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.palette.graphics.Palette
import com.plcoding.jetpackcomposepokedex.Repository.PokemonRepository
import com.plcoding.jetpackcomposepokedex.Util.Resource
import com.plcoding.jetpackcomposepokedex.data.models.PokedexListEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject



@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) :ViewModel(){

    private var currPage=0
    val pokemonList= mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError= mutableStateOf("")
    var isloading = mutableStateOf(false)
    var endReached= mutableStateOf(false)

    private var cachedPokemonList= listOf<PokedexListEntry>()
    private var isSearchStarting=true
    var isSearching= mutableStateOf(false)



    init {
        loadPokemonPaginated()
    }

    fun searchPokemon(query:String){
        val lisToSearch=if(isSearchStarting){
            pokemonList.value
        }else{
            cachedPokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()){
                pokemonList.value=cachedPokemonList
                isSearchStarting=true
                isSearching.value=false
                return@launch
            }
            /*
            *If query parameter or index number does not match, it is deleted.
             */
            val results=lisToSearch.filter {
            it.pokemonName.contains(query.trim(),ignoreCase = true)||it.nunber.toString()==query.trim()
            }
            if (isSearchStarting){
                cachedPokemonList=pokemonList.value
                isSearchStarting=false
            }
            pokemonList.value=results
            isSearching.value=true
        }
    }
     fun loadPokemonPaginated (){

        viewModelScope.launch{
            isloading.value=false
            val result =repository.getPokemonListRepo(20,20*currPage)
            when (result){
                is Resource.Success->{
                    endReached.value=currPage*20 >= result.data!!.count

                    val poxedexEntries=result.data.results.mapIndexed{index,entry->

                        /*
                        Parses index number in url
                        */
                        val nunber=if(entry.url.endsWith("/")){
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        }else{
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url ="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${nunber}.png"

                        //capitalize the first letter of the word

                        PokedexListEntry(entry.name.capitalize(java.util.Locale.ROOT),url, nunber.toInt())
                    }
                    currPage++
                    loadError.value=""
                    isloading.value=false
                    pokemonList.value+=poxedexEntries


                }

                is Resource.Error->{
                    loadError.value=result.message!!
                    isloading.value=false
                }
            }
        }
    }


    fun calculateDominantColor(drawable: Drawable,onfinish:(Color)->Unit){
        Timber.d("calulate")

        val bmp=(drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888,true)

        Palette.Builder(bmp).generate{
            it?.dominantSwatch?.rgb?.let {int->
                onfinish(Color(int))
            }
        }
    }
}