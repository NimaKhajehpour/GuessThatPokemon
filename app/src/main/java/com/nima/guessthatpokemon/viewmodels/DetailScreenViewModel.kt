package com.nima.guessthatpokemon.viewmodels

import androidx.lifecycle.ViewModel
import com.nima.guessthatpokemon.model.PokemonAbility.PokemonAbility
import com.nima.guessthatpokemon.model.PokemonSpecies.PokemonSpecies
import com.nima.guessthatpokemon.model.pokemon.Pokemon
import com.nima.guessthatpokemon.model.type.PokemonType
import com.nima.guessthatpokemon.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val repository: PokemonRepository)
    : ViewModel() {

    suspend fun getPokemonAbilities(pokemonAbilityIdSet: Set<Int>): List<PokemonAbility>{
        return repository.getPokemonAbility(pokemonAbilityIdSet = pokemonAbilityIdSet)
    }

    suspend fun getPokemonTypes(pokemonTypeIdSet: Set<Int>): List<PokemonType>{
        return repository.getPokemonType(pokemonTypeIdSet = pokemonTypeIdSet)
    }

    suspend fun getPokemonById(pokemonId: Int): Pokemon?{
        return repository.getPokemonById(pokemonId = pokemonId)
    }
}