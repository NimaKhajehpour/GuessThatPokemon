package com.nima.guessthatpokemon.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "",
    shape: Shape? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit = {},

) {
    Button(onClick = {
        onClick()
    },
        modifier = modifier,
        enabled = enabled,
        shape = shape ?: ButtonDefaults.shape,
        colors = colors
    ) {
        Text(text = text)
    }
}