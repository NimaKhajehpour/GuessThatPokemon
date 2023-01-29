package com.nima.guessthatpokemon.components

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun TextButtonComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    style: TextStyle,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    onClick: () -> Unit
) {
    TextButton(onClick = { onClick() },
        modifier = modifier,
        enabled = enabled,
        colors = colors
    ) {
        Text(text = text,
            style = style
        )
    }
}