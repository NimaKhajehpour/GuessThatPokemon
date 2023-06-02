package com.nima.guessthatpokemon.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.guessthatpokemon.BuildConfig
import com.nima.guessthatpokemon.R
import com.nima.guessthatpokemon.components.TitleImage

@Composable
fun AboutScreen(
    navController: NavController
){

    BackHandler(true) {
        navController.popBackStack()
    }

    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleImage()
        Text(text = "GuessThatPokemon V${BuildConfig.VERSION_NAME}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Divider(thickness = 2.dp)

        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "A simple game of",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Light,
            )
            TextButton(onClick = {
                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse("https://www.github.com/NimaKhajehpour/GuessThatPokemon")
                context.startActivity(browserIntent)
            },
                contentPadding = PaddingValues(4.dp),
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text(
                    text = "GuessThatPokemon",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Light,
                )
            }
        }

        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Made By:",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 10.dp)
            )
            
            Button(onClick = { 
                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse("https://www.github.com/NimaKhajehpour")
                context.startActivity(browserIntent)
            },
                shape = RoundedCornerShape(5.dp),
                contentPadding = PaddingValues(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                Image(painter = painterResource(id = R.drawable.github_mark),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 5.dp),
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onTertiaryContainer)
                )
                Text(text = "Nima Khajehpour")
            }
        }

        Button(onClick = {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("https://www.github.com/NimaKhajehpour/GuessThatPokemon/issues")
            context.startActivity(browserIntent)
        },
            shape = RoundedCornerShape(5.dp),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_bug_report_24),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 5.dp)
            )
            Text(text = "Report a Bug/Request Features")
        }
    }
}