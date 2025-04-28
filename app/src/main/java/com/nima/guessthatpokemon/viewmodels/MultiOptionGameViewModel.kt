package com.nima.guessthatpokemon.viewmodels

import androidx.lifecycle.ViewModel
import com.nima.guessthatpokemon.model.Pokemon
import com.nima.guessthatpokemon.repository.PokemonRepository

class MultiOptionGameViewModel (private val repository: PokemonRepository): ViewModel() {

    suspend fun capturePokemon(id: Int) = repository.addPokemon(Pokemon(id))
}