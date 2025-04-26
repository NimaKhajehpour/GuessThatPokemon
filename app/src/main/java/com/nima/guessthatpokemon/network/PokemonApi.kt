package com.nima.guessthatpokemon.network

import com.nima.guessthatpokemon.model.PokemonAbility.PokemonAbility
import com.nima.guessthatpokemon.model.PokemonSpecies.PokemonSpecies
import com.nima.guessthatpokemon.model.pokemon.Pokemon
import com.nima.guessthatpokemon.model.type.PokemonType
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") pokemonId: Int): Pokemon

    @GET("ability/{id}")
    suspend fun getPokemonAbility(@Path("id") pokemonId: Int): PokemonAbility

    @GET("type/{id}")
    suspend fun getPokemonType(@Path("id") pokemonId: Int): PokemonType
}