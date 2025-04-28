package com.nima.guessthatpokemon.util

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import java.util.*

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

    val languageNames = mapOf(
        "Japanese Kana" to "ja-Hrkt",
        "Romaji" to "roomaji",
        "Korean" to "ko",
        "Chinese Traditional" to "zh-Hant",
        "French" to "fr",
        "German" to "de",
        "Spanish" to "es",
        "Italian" to "it",
        "English" to "en",
        "Japanese" to "ja",
        "Chinese Simplified" to "zh-Hans"
    )

    const val usdt_address = "0x55Fd62eC2DB8F3f88d3c5825c68ED4DA2AA1F82c"

    const val btc_address = "1KUNrUPMZvcrQV6Eio3bSS1GgpCJn4Jk6A"

    const val eth_address = "0x55Fd62eC2DB8F3f88d3c5825c68ED4DA2AA1F82c"

    const val ton_address = "UQCZ_C3c9sJwMzfBUukcSq4zBJd2A05DHywxmlkaYPlZb7BV"

    fun findEmptyNamesIndex(lang: String): List<Int>{
        return when(lang){
            "zh-Hant" -> (906..1008).toList()
            "de" -> (906..907).toList()
            "es" -> (899..1008).toList()
            "it" -> (906..1008).toList()
            "zh-Hans" -> (906..1008).toList()
            else -> emptyList()
        }
    }

    fun giveRandomIds(generation: String?, lang: String, size: Int = 20): List<Int>{
        return when(generation){
            null -> (1..1008).toList().subtract(findEmptyNamesIndex(lang).toSet()).shuffled(Random()).subList(0, size)
            "generation-i" -> (1..151).toList().subtract(findEmptyNamesIndex(lang).toSet()).shuffled(Random()).subList(0, size)
            "generation-ii" -> (152..251).toList().subtract(findEmptyNamesIndex(lang).toSet()).shuffled(Random()).subList(0, size)
            "generation-iii" -> (252..386).toList().subtract(findEmptyNamesIndex(lang).toSet()).shuffled(Random()).subList(0, size)
            "generation-iv" -> (387..493).toList().subtract(findEmptyNamesIndex(lang).toSet()).shuffled(Random()).subList(0, size)
            "generation-v" -> (494..649).toList().subtract(findEmptyNamesIndex(lang).toSet()).shuffled(Random()).subList(0, size)
            "generation-vi" -> (650..721).toList().subtract(findEmptyNamesIndex(lang).toSet()).shuffled(Random()).subList(0, size)
            "generation-vii" -> (722..809).toList().subtract(findEmptyNamesIndex(lang).toSet()).shuffled(Random()).subList(0, size)
            "generation-viii" -> (810..905).toList().subtract(findEmptyNamesIndex(lang).toSet()).shuffled(Random()).subList(0, size)
            "generation-ix" -> (906..1008).toList().subtract(findEmptyNamesIndex(lang).toSet()).shuffled(Random()).subList(0, size)
            else -> emptyList()
        }
    }

    fun makeNameValid(name: String): String{
        if (name.contains("♂")){
            return name.replace("♂", "")
        } else if (name.contains("♀")){
            return name.replace("♀", "")
        }else if (name.contains("’")){
            return name.replace("’", "\'")
        }
        return name

    }

    @Composable
    fun calculateButtonColor(answered: Boolean, correct: Boolean): ButtonColors{
        if (!answered){
            return ButtonDefaults.buttonColors()
        }else{
            if (correct){
                return ButtonDefaults.buttonColors(containerColor = Color(0xFF045C04))
            }else{
                return ButtonDefaults.buttonColors(containerColor = Color(0xFF970303))
            }
        }
    }
}