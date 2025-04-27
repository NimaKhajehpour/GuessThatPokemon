package com.nima.guessthatpokemon.di

import android.content.Context
import androidx.room.Room
import com.nima.guessthatpokemon.database.PokemonDao
import com.nima.guessthatpokemon.database.PokemonDatabase
import com.nima.guessthatpokemon.repository.PokemonRepository
import com.nima.guessthatpokemon.util.Constants
import com.nima.guessthatpokemon.viewmodels.PokemonGameViewModel
import com.nima.guessthatpokemon.viewmodels.PokemonListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val module = module{

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
        PokemonRepository(get())
    }

    viewModel {
        PokemonListViewModel(get())
    }
    viewModel {
        PokemonGameViewModel(get())
    }

}