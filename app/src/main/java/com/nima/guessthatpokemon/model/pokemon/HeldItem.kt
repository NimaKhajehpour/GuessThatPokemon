package com.nima.guessthatpokemon.model.pokemon

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)