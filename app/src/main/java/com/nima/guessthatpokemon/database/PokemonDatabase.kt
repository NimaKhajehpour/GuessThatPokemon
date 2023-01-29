package com.nima.guessthatpokemon.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nima.guessthatpokemon.model.Pokemon

@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun PokemonDao(): PokemonDao
}