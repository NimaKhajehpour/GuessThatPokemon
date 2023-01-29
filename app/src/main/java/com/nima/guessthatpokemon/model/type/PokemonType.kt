package com.nima.guessthatpokemon.model.type

data class PokemonType(
    val damage_relations: DamageRelations,
    val game_indices: List<GameIndice>,
    val generation: GenerationX,
    val id: Int,
    val move_damage_class: MoveDamageClass,
    val moves: List<Move>,
    val name: String,
    val names: List<Name>,
    val past_damage_relations: List<Any>,
    val pokemon: List<Pokemon>
)