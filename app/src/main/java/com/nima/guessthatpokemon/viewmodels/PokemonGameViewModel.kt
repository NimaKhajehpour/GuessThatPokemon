package com.nima.guessthatpokemon.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.guessthatpokemon.model.pokemon.Pokemon
import com.nima.guessthatpokemon.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonGameViewModel (private val repository: PokemonRepository)
    : ViewModel() {

    var pokemonIndex: MutableState<List<Int>>? = null

    init {
        pokemonIndex = mutableStateOf(
            (0..905).toList().shuffled(java.util.Random()).subList(0, 20)
        )
    }

    fun addPokemon(pokemon: com.nima.guessthatpokemon.model.Pokemon)
        = viewModelScope.launch {
        repository.addPokemon(pokemon)
    }
}