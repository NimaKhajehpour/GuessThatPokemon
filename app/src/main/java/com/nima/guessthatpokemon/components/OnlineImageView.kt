package com.nima.guessthatpokemon.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnlineImageView(
    modifier: Modifier = Modifier,
    imageLink: String = "",
    colorFilter: ColorFilter?,
) {
    var loading by remember {
        mutableStateOf(true)
    }


    Box(
        contentAlignment = Alignment.Center,
        propagateMinConstraints = true
    ) {
        AsyncImage(model = imageLink,
            contentDescription = "Image",
            modifier = modifier,
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            colorFilter = colorFilter,
            onSuccess = { loading = false },
            onLoading = {loading = true}
        )
        AnimatedVisibility (loading){
            CircularProgressIndicator()
        }
    }
}