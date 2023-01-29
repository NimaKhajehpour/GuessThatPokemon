package com.nima.guessthatpokemon.util

import androidx.compose.ui.graphics.Color

object Constants {
    const val baseUrl = "https://pokeapi.co/api/v2/"

    fun getTypeColor(type: String): Color {
        return when(type){
            "normal" -> Color(0xffA9A978)
            "fighting" -> Color(0xffC13029)
            "flying" -> Color(0xffA891F1)
            "poison" -> Color(0xffA041A1)
            "ground" -> Color(0xffE1C168)
            "rock" -> Color(0xffB9A039)
            "bug" -> Color(0xffA9B920)
            "ghost" -> Color(0xff715998)
            "steel" -> Color(0xffB9B8D1)
            "fire" -> Color(0xffF08030)
            "water" -> Color(0xff6891F1)
            "grass" -> Color(0xff78C951)
            "electric" -> Color(0xffF9D130)
            "psychic" -> Color(0xffF95889)
            "ice" -> Color(0xff99D9D8)
            "dragon" -> Color(0xff7139F8)
            "dark" -> Color(0xff715948)
            "fairy" -> Color(0xffF1B6BD)
            "shadow" -> Color(0xff715998)
            else -> Color(0xff6BA496)

        }
    }
}