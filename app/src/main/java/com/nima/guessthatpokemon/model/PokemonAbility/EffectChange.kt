package com.nima.guessthatpokemon.model.PokemonAbility

data class EffectChange(
    val effect_entries: List<EffectEntry>,
    val version_group: VersionGroup
)