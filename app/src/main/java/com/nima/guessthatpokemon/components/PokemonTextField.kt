package com.nima.guessthatpokemon.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonTextField (
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    value: String = "",
    isError: Boolean = false,
    supportingText: String = "",
    textFieldColors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    label: @Composable () -> Unit = {},
    onValueChange: (String) -> Unit = {}
){
    OutlinedTextField(
        readOnly = readOnly,
        value = value,
        onValueChange = {onValueChange(it)},
        modifier = modifier,
        enabled = enabled,
        label = {label()},
        singleLine = singleLine,
        shape = RoundedCornerShape(5.dp),
        keyboardOptions = keyboardOptions,
        isError = isError,
        visualTransformation = visualTransformation,
        supportingText = {
            Text(text = supportingText)
        },
        trailingIcon = trailingIcon,
        colors = textFieldColors
    )
}