package com.nima.guessthatpokemon.components

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PokemonProgressBar(
    modifier: Modifier = Modifier,
    progress: Float = 0f
) {
    LinearProgressIndicator(
        progress = progress,
        modifier = modifier,
        trackColor = MaterialTheme.colorScheme.tertiaryContainer
    )
}