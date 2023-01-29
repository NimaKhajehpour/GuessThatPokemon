package com.nima.guessthatpokemon.model.pokemon

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)