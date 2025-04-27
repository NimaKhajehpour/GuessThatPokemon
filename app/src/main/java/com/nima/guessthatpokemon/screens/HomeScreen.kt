package com.nima.guessthatpokemon.screens

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.guessthatpokemon.R
import com.nima.guessthatpokemon.ThemeDataStore
import com.nima.guessthatpokemon.components.ButtonComponent
import com.nima.guessthatpokemon.components.TitleImage
import com.nima.guessthatpokemon.navigation.PokemonScreens
import com.nima.guessthatpokemon.util.Constants
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    val activity = LocalActivity.current

    val context = LocalContext.current

    val themeDataStore = ThemeDataStore(context)

    var showSettingDialog by remember {
        mutableStateOf(false)
    }

    val isDark =
        themeDataStore.getTheme.collectAsState(initial = false).value ?: false

    val scope = rememberCoroutineScope()

    var selectedLanguage by remember {
        mutableStateOf(context.getString(R.string.en))
    }

    var selectedLangToShow by remember {
        mutableStateOf("English")
    }

    var selectedGeneration by remember {
        mutableStateOf("1")
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


                               Text(text = stringResource(R.string.start_game_prompt),
                                   modifier = Modifier.padding(bottom = 12.dp),
                                   textAlign = TextAlign.Center
                               )

                               ExposedDropdownMenuBox(
                                   expanded = languageMenuExpanded,
                                   onExpandedChange = {languageMenuExpanded = !languageMenuExpanded}
                               ) {
                                   OutlinedTextField(value = selectedLangToShow,
                                       onValueChange = {},
                                       modifier = Modifier.menuAnchor(),
                                       readOnly = true,
                                       label = {
                                           Text(text = stringResource(R.string.language))
                                       },
                                       trailingIcon = {
                                           ExposedDropdownMenuDefaults.TrailingIcon(expanded = languageMenuExpanded)
                                       },
                                       colors = ExposedDropdownMenuDefaults.textFieldColors()
                                   )
                                   ExposedDropdownMenu(
                                       expanded = languageMenuExpanded,
                                       onDismissRequest = { languageMenuExpanded = false }) {
                                       Constants.languageNames.forEach{
                                           DropdownMenuItem(
                                               text = { Text(text = it.key) },
                                               onClick = {
                                                   selectedLanguage = it.value
                                                   selectedLangToShow = it.key
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
                                           Text(text = stringResource(R.string.generation))
                                       },
                                       trailingIcon = {
                                           ExposedDropdownMenuDefaults.TrailingIcon(expanded = generationMenuExpanded)
                                       },
                                       colors = ExposedDropdownMenuDefaults.textFieldColors()
                                   )
                                   ExposedDropdownMenu(
                                       expanded = generationMenuExpanded,
                                       onDismissRequest = { generationMenuExpanded = false }) {
                                       listOf("1", "2", "3", "4", "5", "6", "7", "8", "9").forEach{
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
                        Text(text = stringResource(R.string.game_settings))
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
                            Text(text = stringResource(R.string.start_game))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showSettingDialog = false
                        }) {
                            Text(text = stringResource(R.string.cancel))
                        }
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
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
                                        painter = painterResource(id = R.drawable.ic_baseline_dark_mode_24),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                    Text(text = stringResource(R.string.dark),
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
                                    painter = painterResource(id = R.drawable.ic_baseline_light_mode_24),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.tertiary
                                )

                                Text(text = stringResource(R.string.light),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                            }
                        }
                    }

                }

                IconButton(onClick = {
                    navController.navigate(PokemonScreens.AboutScreen.name)
                },
                    modifier = Modifier.size(48.dp)

                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary
                        )

                        Text(text = stringResource(R.string.about),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }

                IconButton(onClick = {
                    navController.navigate(PokemonScreens.DonateScreen.name)
                },
                    modifier = Modifier.size(48.dp)

                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Icon(
                            painter = painterResource(R.drawable.donate),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary
                        )

                        Text(text = stringResource(R.string.donate),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }

                IconButton(onClick = {
                    navController.navigate(PokemonScreens.SocialsScreen.name)
                },
                    modifier = Modifier.size(48.dp)

                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Icon(
                            painter = painterResource(R.drawable.socials),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary
                        )

                        Text(text = stringResource(R.string.socials),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }

                IconButton(onClick = {
                    if (activity != null) {
                        activity.finish()
                    }
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

                        Text(text = stringResource(R.string.exit),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }

            TitleImage()

            Text(text = stringResource(R.string.guess_that_pokemon),
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
                text = stringResource(R.string.start_game)
            ){
                showSettingDialog = true
            }

            ButtonComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, bottom = 8.dp),
                enabled = true,
                text = stringResource(R.string.your_collection)
            ){
                // Go to collections
                navController.navigate(PokemonScreens.CollectionScreen.name)
            }

//            ButtonComponent(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 32.dp, end = 32.dp, bottom = 8.dp),
//                enabled = true,
//                text = stringResource(R.string.settings)
//            ){
//                // go to settings screen
//            }
        }
    }
}