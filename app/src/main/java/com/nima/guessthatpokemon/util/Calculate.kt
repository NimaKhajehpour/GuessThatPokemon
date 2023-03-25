package com.nima.guessthatpokemon.util

import androidx.compose.ui.text.toLowerCase

object Calculate {

    fun calculateWeakness(name: String): List<String>{
        when(name.toLowerCase()) {
            "bug" -> return listOf("fire", "flying", "rock")
            "dark" -> return listOf("bug", "fairy", "fighting")
            "dragon" -> return listOf("dragon", "fairy", "ice")
            "electric" -> return listOf("ground")
            "fairy" -> return listOf("poison", "steel")
            "fighting" -> return listOf("fairy", "flying", "psychic")
            "fire" -> return listOf("ground", "rock", "water")
            "flying" -> return listOf("electric", "ice", "rock")
            "ghost" -> return listOf("dark", "ghost")
            "grass" -> return listOf("bug", "fire", "flying", "ice", "poison")
            "ground" -> return listOf("grass", "ice", "water")
            "ice" -> return listOf("fighting", "fire", "rock", "steel")
            "normal" -> return listOf("fighting")
            "poison" -> return listOf("ground", "psychic")
            "psychic" -> return listOf("bug", "dark", "ghost")
            "rock" -> return listOf("fighting", "grass", "ground", "steel", "water")
            "steel" -> return listOf("fighting", "fire", "ground")
            "water" -> return listOf("electric", "grass")
            else -> return emptyList()
        }
    }
}