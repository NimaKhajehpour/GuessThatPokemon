package com.nima.guessthatpokemon.di

import com.nima.guessthatpokemon.network.PokemonApi
import com.nima.guessthatpokemon.repository.PokemonRepository
import com.nima.guessthatpokemon.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Provides
    @Singleton
    fun providePokemonRepository(api: PokemonApi) = PokemonRepository(api)

    @Provides
    @Singleton
    fun providePokemonApi(): PokemonApi =
        Retrofit.Builder().baseUrl(Constants.baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build().create(PokemonApi::class.java)
}