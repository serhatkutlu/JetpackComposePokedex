package com.plcoding.jetpackcomposepokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.sp
import androidx.navigation.NavType

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument

import androidx.navigation.compose.rememberNavController
import com.plcoding.jetpackcomposepokedex.ui.theme.JetpackComposePokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePokedexTheme {
                val navController= rememberNavController()
                NavHost(navController = navController, startDestination = "pokemon_list_screen" ){
                    composable("pokemon_list_screen"){

                    }
                    composable("pokemon_detail_screen/{dominantColor}/{pokemonName}",
                    arguments = listOf(
                        navArgument("dominantColor"){
                            type= NavType.IntType
                        },
                        navArgument("pokemonName"){
                            type= NavType.StringType
                        }
                    )){
                        val dominantColor=remember{
                            val color=it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) }?: Color.White
                        }
                        val pokemonName= remember {
                            it.arguments?.getString("pokemonName")
                        }

                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun test(){
        Box(modifier = Modifier
            .background(Color.Blue)
            .fillMaxSize()){
            Text(text = "TEST", textAlign = TextAlign.Center, fontSize = 40.sp, modifier = Modifier.align(Alignment.Center))
        }

    }
}
