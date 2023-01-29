package com.nima.guessthatpokemon.viewmodels

import androidx.lifecycle.ViewModel
import com.nima.guessthatpokemon.model.pokemon.Pokemon
import com.nima.guessthatpokemon.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val repository: PokemonRepository)
    :ViewModel(){

    suspend fun getPokemons(pokemonIdSet: List<Int>): MutableList<Pokemon?> {
        return repository.getPokemonById(pokemonIdSet = pokemonIdSet!!)
    }
}