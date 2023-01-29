package com.nima.guessthatpokemon.components

import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.nima.guessthatpokemon.R

@Composable
fun TitleImage(
    painter: Painter = painterResource(id = R.drawable.pikachu)
) {
    Image(
        painter = painter,
        contentDescription = null,
        alignment = Alignment.Center,
        contentScale = ContentScale.Inside
    )
}