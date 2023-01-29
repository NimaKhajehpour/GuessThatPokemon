package com.nima.guessthatpokemon.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nima.guessthatpokemon.screens.DetailScreen
import com.nima.guessthatpokemon.screens.GameScreen
import com.nima.guessthatpokemon.screens.HomeScreen
import com.nima.guessthatpokemon.screens.PokemonCollection

@Composable
fun PokemonNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = PokemonScreens.HomeScreen.name){
        composable(PokemonScreens.HomeScreen.name){
            HomeScreen(
                navController = navController
            )
        }
        composable(PokemonScreens.GameScreen.name){
            GameScreen(navController = navController, viewModel = hiltViewModel())
        }
        composable(PokemonScreens.CollectionScreen.name){
            PokemonCollection(navController = navController)

        }
        composable(PokemonScreens.DetailScreen.name+"/{id}",
            arguments = listOf(
                navArgument(name = "id"){
                    type = NavType.IntType
                }
            )
        ){
            DetailScreen(navController = navController,
                pokemonId = it.arguments?.getInt("id")
            )
        }
    }
}