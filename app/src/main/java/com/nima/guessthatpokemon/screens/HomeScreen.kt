package com.nima.guessthatpokemon.screens

import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nima.guessthatpokemon.R
import com.nima.guessthatpokemon.ThemeDataStore
import com.nima.guessthatpokemon.components.ButtonComponent
import com.nima.guessthatpokemon.components.TitleImage
import com.nima.guessthatpokemon.navigation.PokemonScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    val activity = LocalContext.current as Activity

    val context = LocalContext.current

    val themeDataStore = ThemeDataStore(context)

    val isDark =
        themeDataStore.getTheme.collectAsState(initial = false).value ?: false

    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = {
                    scope.launch {
                        themeDataStore.saveTheme(!isDark)
                    }
                },
                    modifier = Modifier.size(48.dp),
                ) {

                    AnimatedContent(targetState = isDark) {
                        when (it){
                            true ->{
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_light_mode_24),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                    Text(text = "Light",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                }
                            }else -> {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_dark_mode_24),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.tertiary
                                )

                                Text(text = "Dark",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    activity.finish()
                },
                    modifier = Modifier.size(48.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_exit_to_app_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary
                        )

                        Text(text = "Exit",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }

            TitleImage()

            Text(text = "Guess That Pokemon",
                modifier = Modifier.padding(horizontal = 32.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )

            ButtonComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, bottom = 8.dp),
                enabled = true,
                text = "Start Game"
            ){
                navController.navigate(PokemonScreens.GameScreen.name)
            }

            ButtonComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, bottom = 8.dp),
                enabled = true,
                text = "Your Collection"
            ){
                // Go to collections
                navController.navigate(PokemonScreens.CollectionScreen.name)
            }
        }
    }
}