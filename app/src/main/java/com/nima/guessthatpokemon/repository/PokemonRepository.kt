package com.nima.guessthatpokemon.repository

import com.nima.guessthatpokemon.database.PokemonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class PokemonRepository (private val dao: PokemonDao) {

    fun getAllPokemons(): Flow<List<com.nima.guessthatpokemon.model.Pokemon>>
        = dao.getAllPokemons().flowOn(Dispatchers.IO).conflate()

    suspend fun addPokemon(pokemon: com.nima.guessthatpokemon.model.Pokemon)
        = dao.addPokemon(pokemon)

}