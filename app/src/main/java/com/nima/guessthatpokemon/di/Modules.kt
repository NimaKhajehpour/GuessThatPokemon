package com.nima.guessthatpokemon.di

import android.content.Context
import androidx.room.Room
import com.nima.guessthatpokemon.database.PokemonDao
import com.nima.guessthatpokemon.database.PokemonDatabase
import com.nima.guessthatpokemon.network.PokemonApi
import com.nima.guessthatpokemon.repository.PokemonRepository
import com.nima.guessthatpokemon.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Provides
    @Singleton
    fun providePokemonRepository(api: PokemonApi, dao: PokemonDao)
        = PokemonRepository(api = api, dao = dao)

    @Provides
    @Singleton
    fun providePokemonApi(): PokemonApi =
        Retrofit.Builder().baseUrl(Constants.baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build().create(PokemonApi::class.java)

    @Singleton
    @Provides
    fun providePokemonDao(pokemonDatabase: PokemonDatabase): PokemonDao
            = pokemonDatabase.PokemonDao()

    @Singleton
    @Provides
    fun providePokemonDatabase(@ApplicationContext context: Context): PokemonDatabase
            = Room.databaseBuilder(
        context,
        PokemonDatabase::class.java,
        "PokemonDatabase"
    ).fallbackToDestructiveMigration().build()
}