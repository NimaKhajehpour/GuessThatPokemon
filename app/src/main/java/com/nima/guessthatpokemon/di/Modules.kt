package com.nima.guessthatpokemon.di

import android.content.Context
import androidx.room.Room
import com.nima.guessthatpokemon.database.PokemonDao
import com.nima.guessthatpokemon.database.PokemonDatabase
import com.nima.guessthatpokemon.network.PokemonApi
import com.nima.guessthatpokemon.repository.PokemonRepository
import com.nima.guessthatpokemon.util.Constants
import com.nima.guessthatpokemon.viewmodels.DetailScreenViewModel
import com.nima.guessthatpokemon.viewmodels.PokemonGameViewModel
import com.nima.guessthatpokemon.viewmodels.PokemonListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val module = module{

    single {
        Retrofit.Builder().baseUrl(Constants.baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build().create(PokemonApi::class.java)
    }

    single {
        Room.databaseBuilder(
                context = androidApplication(),
                PokemonDatabase::class.java,
                "PokemonDatabase"
            ).fallbackToDestructiveMigration(false).build()
    }

    single {
        val database = get<PokemonDatabase>()
        database.PokemonDao()
    }

    single {
        PokemonRepository(get(), get())
    }

    viewModel {
        PokemonListViewModel(get())
    }
    viewModel {
        PokemonGameViewModel(get())
    }
    viewModel {
        DetailScreenViewModel(get())
    }

}