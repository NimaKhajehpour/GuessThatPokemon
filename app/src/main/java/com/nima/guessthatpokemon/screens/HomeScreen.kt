package com.nima.guessthatpokemon.screens

import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.guessthatpokemon.R
import com.nima.guessthatpokemon.ThemeDataStore
import com.nima.guessthatpokemon.components.ButtonComponent
import com.nima.guessthatpokemon.components.TitleImage
import com.nima.guessthatpokemon.navigation.PokemonScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    val activity = LocalContext.current as Activity

    val context = LocalContext.current

    val themeDataStore = ThemeDataStore(context)

    var showSettingDialog by remember {
        mutableStateOf(false)
    }

    val isDark =
        themeDataStore.getTheme.collectAsState(initial = false).value ?: false

    val scope = rememberCoroutineScope()

    var selectedLanguage by remember {
        mutableStateOf("en")
    }

    var selectedGeneration by remember {
        mutableStateOf("All")
    }

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

            if (showSettingDialog){
                AlertDialog(onDismissRequest = {
                    showSettingDialog = false
                },
                    text = {
                           Column(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .verticalScroll(rememberScrollState()),
                               verticalArrangement = Arrangement.Top,
                               horizontalAlignment = Alignment.CenterHorizontally
                           ) {
                               var languageMenuExpanded by remember {
                                   mutableStateOf(false)
                               }
                               var generationMenuExpanded by remember {
                                   mutableStateOf(false)
                               }


                               Text(text = "With this version of the game you have the option to change the language of the names on Pokemons and the" +
                                       "Generation of them so that they are filtered as you like. Although the problem with the language selection is that it is not complete on languages other than English" +
                                       "and you might not have a complete GuessThatPokemon experience!",
                                   modifier = Modifier.padding(bottom = 8.dp),
                                   textAlign = TextAlign.Center
                               )

                               ExposedDropdownMenuBox(
                                   expanded = languageMenuExpanded,
                                   onExpandedChange = {languageMenuExpanded = !languageMenuExpanded}
                               ) {
                                   OutlinedTextField(value = selectedLanguage,
                                       onValueChange = {},
                                       modifier = Modifier.menuAnchor(),
                                       readOnly = true,
                                       label = {
                                           Text(text = "Language")
                                       },
                                       trailingIcon = {
                                           ExposedDropdownMenuDefaults.TrailingIcon(expanded = languageMenuExpanded)
                                       },
                                       colors = ExposedDropdownMenuDefaults.textFieldColors()
                                   )
                                   ExposedDropdownMenu(
                                       expanded = languageMenuExpanded,
                                       onDismissRequest = { languageMenuExpanded = false }) {
                                       listOf("ja-Hrkt", "roomaji", "ko", "zh-Hant", "fr", "de", "es", "it", "en", "cs", "ja", "zh-Hans", "pt-BR").forEach{
                                           DropdownMenuItem(
                                               text = { Text(text = it) },
                                               onClick = {
                                                   selectedLanguage = it
                                                   languageMenuExpanded = false
                                               },
                                               contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                           )
                                       }
                                   }
                               }

                               ExposedDropdownMenuBox(
                                   expanded = generationMenuExpanded,
                                   onExpandedChange = {generationMenuExpanded = !generationMenuExpanded},
                                   modifier = Modifier.padding(vertical = 8.dp)
                               ) {
                                   OutlinedTextField(value = selectedGeneration,
                                       onValueChange = {},
                                       modifier = Modifier.menuAnchor(),
                                       readOnly = true,
                                       label = {
                                           Text(text = "Generation")
                                       },
                                       trailingIcon = {
                                           ExposedDropdownMenuDefaults.TrailingIcon(expanded = generationMenuExpanded)
                                       },
                                       colors = ExposedDropdownMenuDefaults.textFieldColors()
                                   )
                                   ExposedDropdownMenu(
                                       expanded = generationMenuExpanded,
                                       onDismissRequest = { generationMenuExpanded = false }) {
                                       listOf("All", "1", "2", "3", "4", "5", "6", "7", "8", "9").forEach{
                                           DropdownMenuItem(
                                               text = { Text(text = it) },
                                               onClick = {
                                                   selectedGeneration = it
                                                   generationMenuExpanded = false
                                               },
                                               contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                           )
                                       }
                                   }
                               }
                           }
                    },
                    title = {
                        Text(text = "Game Settings")
                    },
                    icon = {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showSettingDialog = false

                            //go with desired settings
                            navController.navigate(PokemonScreens.GameScreen.name+"/$selectedLanguage/$selectedGeneration"
                            )
                        }) {
                            Text(text = "Start Game")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showSettingDialog = false
                        }) {
                            Text(text = "Cancel")
                        }
                    }
                )
            }

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
                showSettingDialog = true
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