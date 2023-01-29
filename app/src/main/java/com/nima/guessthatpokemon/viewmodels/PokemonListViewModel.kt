package com.nima.guessthatpokemon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.guessthatpokemon.model.pokemon.Pokemon
import com.nima.guessthatpokemon.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val repository: PokemonRepository)
    :ViewModel(){

    suspend fun getPokemons(pokemonIdSet: List<Int>): MutableList<Pokemon?> {
        return repository.getPokemonById(pokemonIdSet = pokemonIdSet!!)
    }

    private val _pokemons = MutableStateFlow<List<com.nima.guessthatpokemon.model.Pokemon>>(
        emptyList()
    )

    val pokemonList = _pokemons.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllPokemons().distinctUntilChanged().collect{
                _pokemons.value = it
            }
        }
    }
}