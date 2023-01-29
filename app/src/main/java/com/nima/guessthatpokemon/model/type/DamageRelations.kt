package com.nima.guessthatpokemon.model.type

data class DamageRelations(
    val double_damage_from: List<DoubleDamageFrom>,
    val double_damage_to: List<DoubleDamageTo>,
    val half_damage_from: List<HalfDamageFrom>,
    val half_damage_to: List<HalfDamageTo>,
    val no_damage_from: List<Any>,
    val no_damage_to: List<Any>
)