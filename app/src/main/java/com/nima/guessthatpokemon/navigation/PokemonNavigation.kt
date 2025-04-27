package com.nima.guessthatpokemon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nima.guessthatpokemon.screens.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = PokemonScreens.HomeScreen.name){
        composable(PokemonScreens.HomeScreen.name){
            HomeScreen(
                navController = navController
            )
        }
        composable(PokemonScreens.GameScreen.name+"/{lang}/{gen}",
            arguments = listOf(
                navArgument(name = "lang"){type = NavType.StringType},
                navArgument(name = "gen"){type = NavType.StringType},
            )
        ){
            GameScreen(navController = navController, viewModel = koinViewModel(),
                lang = it.arguments?.getString("lang"),
                gen = it.arguments?.getString("gen"),
            )
        }
        composable(PokemonScreens.CollectionScreen.name){
            PokemonCollection(navController = navController, koinViewModel())

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

        composable(PokemonScreens.AboutScreen.name){
            AboutScreen(navController = navController)
        }
    }
}