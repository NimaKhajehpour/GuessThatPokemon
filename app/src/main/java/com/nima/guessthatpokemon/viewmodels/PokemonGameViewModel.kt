package com.nima.guessthatpokemon.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nima.guessthatpokemon.model.pokemon.Pokemon
import com.nima.guessthatpokemon.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonGameViewModel @Inject constructor(private val repository: PokemonRepository)
    : ViewModel() {

    suspend fun getPokemons(pokemonIdSet: List<Int>?): MutableList<Pokemon?> {
        return repository.getPokemonById(pokemonIdSet = pokemonIdSet!!)
    }

    var pokemonIndex: MutableState<List<Int>>? = null

    init {
        pokemonIndex = mutableStateOf(
            (0..905).toList().shuffled(java.util.Random()).subList(0, 20)
        )
    }
}