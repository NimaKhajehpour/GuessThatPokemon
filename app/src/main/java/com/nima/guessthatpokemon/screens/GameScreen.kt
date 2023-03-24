package com.nima.guessthatpokemon.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.guessthatpokemon.components.*
import com.nima.guessthatpokemon.model.pokemon.Pokemon
import com.nima.guessthatpokemon.viewmodels.PokemonGameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel as ViewModel

@Composable
fun GameScreen(
    navController: NavController,
    viewModel: PokemonGameViewModel
){

    val context = LocalContext.current
    val randomIndex = remember{ mutableStateOf(viewModel.pokemonIndex?.value) }

    if (randomIndex.value?.size == 20){
        val pokemonList = produceState<MutableList<Pokemon?>?>(initialValue = null){
            value = viewModel.getPokemons(pokemonIdSet = randomIndex.value)
        }.value

        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility (pokemonList == null){
                    LoadingDialog()
                }
                AnimatedVisibility (pokemonList != null && pokemonList.size == 0){
                    // Could not connect to api
                    ErrorDialog{
                        navController.popBackStack()
                    }
                }
                AnimatedVisibility( pokemonList != null && pokemonList.size > 0){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        var pokemonListIndex by remember {
                            mutableStateOf(0)
                        }
                        val pokemonName =
                            pokemonList!![pokemonListIndex]?.name

                        Log.d("LOL", "GameScreen: $pokemonName")

                        val pokemonImageLink =
                            "https://raw.githubusercontent.com/PokeAPI/" +
                                    "sprites/master/sprites/pokemon/other/" +
                                    "official-artwork/${pokemonList[pokemonListIndex]?.id}.png"

                        val pokemonImageTint = remember {
                            mutableStateOf<ColorFilter?>(ColorFilter.tint(color = Color.Black))
                        }

                        var chances by remember{
                            mutableStateOf(0)
                        }

                        var textFieldEnabled by remember {
                            mutableStateOf(true)
                        }

                        var textFieldValue by remember{
                            mutableStateOf("")
                        }

                        var goNext by remember{
                            mutableStateOf(false)
                        }

                        var progress by remember{
                            mutableStateOf(0)
                        }

                        Box(
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
                        ){

                            PokemonProgressBar(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .border(
                                        width = .1.dp,
                                        shape = RoundedCornerShape(50),
                                        color = Color.Transparent
                                    )
                                    .height(50.dp)
                                    .fillMaxWidth(),
                                progress = (progress * 100 / 20f) / 100f
                            )
                            Text(text = "$progress/20",
                                modifier = Modifier.align(Alignment.Center),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        ElevatedCard(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(32.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                OnlineImageView(
                                    colorFilter = pokemonImageTint.value,
                                    modifier = Modifier.padding(vertical = 32.dp, horizontal = 32.dp),
                                    imageLink = pokemonImageLink
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 32.dp, end = 32.dp, bottom = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    repeat(3){ index ->
                                        Icon(imageVector = Icons.Rounded.Close,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(animateDpAsState(targetValue = if (chances >= index + 1) 64.dp else 48.dp).value)
                                                .padding(end = 8.dp),
                                            tint = animateColorAsState(targetValue = if (chances >= index + 1 ){
                                                Color.Red
                                            }else{
                                                Color.LightGray
                                            }).value
                                        )
                                    }
                                }
                                if (chances == 3){
                                    Text(text = pokemonName!!,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    textFieldEnabled = false
                                }
                                Text(text = "Guess That Pokemon",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

                                PokemonTextField(
                                    modifier = Modifier
                                        .padding(bottom = 8.dp,)
                                        .fillMaxWidth(),
                                    enabled = textFieldEnabled,
                                    singleLine = true,
                                    value = textFieldValue,
                                    label = {
                                        Text(text = "Pokemon Name")
                                    }
                                ){
                                    textFieldValue = it
                                }

                                ButtonComponent(
                                    modifier = Modifier
                                        .padding(bottom = 32.dp, top = 16.dp)
                                        .fillMaxWidth(),
                                    text = if (goNext || chances == 3) "Next" else "Submit",
                                    shape = AbsoluteRoundedCornerShape(
                                        bottomLeft = 15.dp, bottomRight = 10.dp
                                    ),
                                ){
                                    // game logic
                                    if (!goNext){
                                        if (textFieldValue.isNotBlank()){
                                            if (chances < 3) {
                                                if (textFieldValue.trim()
                                                        .equals(pokemonName, ignoreCase = true)
                                                ) {
                                                    Toast.makeText(context, "Pokemon Captured", Toast.LENGTH_SHORT)
                                                        .show()
                                                    val pokemon =
                                                        com.nima.guessthatpokemon.model.Pokemon(pokemonList[pokemonListIndex]!!.id)

                                                    viewModel.addPokemon(pokemon)
                                                    pokemonImageTint.value = null
                                                    textFieldEnabled = false
                                                    goNext = true
                                                } else {
                                                    if (chances == 2){
                                                        goNext = true
                                                        pokemonImageTint.value = null
                                                        textFieldEnabled = false
                                                    }
                                                    chances++
                                                    textFieldValue = ""

                                                }
                                            }
                                        }
                                    }else if (goNext){
                                        if (pokemonListIndex != 19){
                                            pokemonImageTint.value = ColorFilter.tint(Color.Black)
                                            pokemonListIndex ++
                                            chances = 0
                                            textFieldEnabled = true
                                            textFieldValue = ""
                                            goNext = false
                                            progress ++
                                        }else{
                                            navController.popBackStack()
                                            progress ++
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    
}