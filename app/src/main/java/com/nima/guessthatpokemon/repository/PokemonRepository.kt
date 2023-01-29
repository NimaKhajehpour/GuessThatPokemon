package com.nima.guessthatpokemon.repository

import com.nima.guessthatpokemon.model.PokemonAbility.PokemonAbility
import com.nima.guessthatpokemon.model.PokemonSpecies.PokemonSpecies
import com.nima.guessthatpokemon.model.pokemon.Pokemon
import com.nima.guessthatpokemon.model.type.PokemonType
import com.nima.guessthatpokemon.network.PokemonApi
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val api: PokemonApi) {

    private var pokemonList: MutableList<Pokemon?> = mutableListOf()

    suspend fun getPokemonById(pokemonIdSet: List<Int>): MutableList<Pokemon?> {
        try {
            pokemonList = mutableListOf()
            for (pokemonId in pokemonIdSet){
                pokemonList.add(api.getPokemonById(pokemonId = pokemonId))
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return pokemonList
    }

    suspend fun getPokemonById(pokemonId: Int): Pokemon? {
        try {
            return api.getPokemonById(pokemonId = pokemonId)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

    suspend fun getPokemonAbility(pokemonAbilityIdSet: Set<Int>): List<PokemonAbility>{
        val pokemonAbilities = mutableListOf<PokemonAbility>()

        try{
            for (id in pokemonAbilityIdSet){
                pokemonAbilities.add(api.getPokemonAbility(pokemonId = id))
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return pokemonAbilities
    }

    suspend fun getPokemonType(pokemonTypeIdSet: Set<Int>): List<PokemonType>{
        val pokemonTypes = mutableListOf<PokemonType>()

        try {
            for (id in pokemonTypeIdSet){
                pokemonTypes.add(api.getPokemonType(pokemonId = id))
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return pokemonTypes
    }
}