package com.nima.guessthatpokemon.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.nima.guessthatpokemon.MultiOptionGameQuery
import com.nima.guessthatpokemon.R
import com.nima.guessthatpokemon.client.apolloClient
import com.nima.guessthatpokemon.components.LoadingDialog
import com.nima.guessthatpokemon.components.OnlineImageView
import com.nima.guessthatpokemon.util.Constants
import com.nima.guessthatpokemon.viewmodels.MultiOptionGameViewModel
import kotlinx.coroutines.launch

@Composable
fun MultiOptionGameScreen(
    navController: NavController,
    viewModel: MultiOptionGameViewModel,
    language: String?
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var selectedPokemonIndex by remember { mutableStateOf(0) }

    val realPokemonIds by remember {
        mutableStateOf(Constants.giveRandomIds(null, language!!, 20))
    }

    val fakePokemonIds by remember {
        mutableStateOf(Constants.giveRandomIds(null, language!!, 60))
    }

    var gameValues: ApolloResponse<MultiOptionGameQuery.Data>? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(Unit) {
        try{
            gameValues = apolloClient.query(
                MultiOptionGameQuery(
                    real_id = Optional.present(realPokemonIds),
                    fake_id = Optional.present(fakePokemonIds),
                    language = language!!
                )
            ).execute()
        }catch (e: Exception){
            Toast.makeText(context, "Error Fetching Data", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    if (gameValues == null){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingDialog()
        }
    }else{
        val pokemonToShow by remember(selectedPokemonIndex) {
            mutableStateOf(gameValues!!.data!!.real_pokemons[selectedPokemonIndex])
        }
        val fakePokemons by remember(selectedPokemonIndex) {
            mutableStateOf(gameValues!!.data!!.fake_pokemons.subList(selectedPokemonIndex*3, (selectedPokemonIndex+1)*3))
        }
        val gameData by remember(pokemonToShow, fakePokemons) {
            mutableStateOf((fakePokemons+pokemonToShow.toFakePokemon()).shuffled())
        }
        val correctAnswerIndex by remember(gameData) {
            mutableStateOf(gameData.indexOf(pokemonToShow.toFakePokemon()))
        }
        val pokemonLink by remember(pokemonToShow) {
            mutableStateOf("https://raw.githubusercontent.com/PokeAPI/" +
            "sprites/master/sprites/pokemon/other/" +
                    "official-artwork/${pokemonToShow.pokemon_species_id}.png")
        }
        var pokemonImageTint by remember {
            mutableStateOf<ColorFilter?>(ColorFilter.tint(color = Color.Black))
        }
        var progress by remember{
            mutableStateOf(0)
        }
        var answered by remember {
            mutableStateOf(false)
        }
        var selectedOption by remember {
            mutableStateOf(-1)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = {
                    progress/20f
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ){
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    OnlineImageView(
                        modifier = Modifier.padding(16.dp),
                        colorFilter = pokemonImageTint,
                        imageLink = pokemonLink
                    )

                    AnimatedVisibility(answered) {
                        Text(
                            text = pokemonToShow.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if (!answered){
                            answered = true
                            selectedOption = 0
                            pokemonImageTint = null
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 3.dp, horizontal = 5.dp),
                    colors = if (selectedOption == 0) Constants.calculateButtonColor(answered = answered, correct = selectedOption == correctAnswerIndex)
                    else ButtonDefaults.buttonColors()
                ) {
                    Text(gameData[0].name)
                }
                Button(
                    onClick = {
                        if (!answered){
                            answered = true
                            selectedOption = 1
                            pokemonImageTint = null
                        }
                    },
                    colors = if (selectedOption == 1) Constants.calculateButtonColor(answered = answered, correct = selectedOption == correctAnswerIndex)
                    else ButtonDefaults.buttonColors(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 3.dp, horizontal = 5.dp),
                ) {
                    Text(gameData[1].name)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if (!answered){
                            answered = true
                            selectedOption = 2
                            pokemonImageTint = null
                        }
                    },
                    colors = if (selectedOption == 2) Constants.calculateButtonColor(answered = answered, correct = selectedOption == correctAnswerIndex)
                    else ButtonDefaults.buttonColors(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 3.dp, horizontal = 5.dp),
                ) {
                    Text(gameData[2].name)
                }
                Button(
                    onClick = {
                        if (!answered){
                            answered = true
                            selectedOption = 3
                            pokemonImageTint = null
                        }
                    },
                    colors = if (selectedOption == 3) Constants.calculateButtonColor(answered = answered, correct = selectedOption == correctAnswerIndex)
                    else ButtonDefaults.buttonColors(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 3.dp, horizontal = 5.dp),
                ) {
                    Text(gameData[3].name)
                }
            }
            Button(
                onClick = {
                    if (selectedPokemonIndex != 19){
                        if (selectedOption == correctAnswerIndex){
                            scope.launch{
                                viewModel.capturePokemon(pokemonToShow.pokemon_species_id!!)
                            }
                            Toast.makeText(context, "Pokemon Captured", Toast.LENGTH_SHORT).show()
                        }
                        progress++
                        pokemonImageTint = ColorFilter.tint(color = Color.Black)
                        answered = false
                        selectedOption = -1
                        selectedPokemonIndex++
                    }else{
                        navController.popBackStack()
                    }
                },
                enabled = answered,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 37.dp, vertical = 5.dp)
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }
}

fun MultiOptionGameQuery.Real_pokemon.toFakePokemon(): MultiOptionGameQuery.Fake_pokemon{
    return MultiOptionGameQuery.Fake_pokemon(name, pokemon_species_id)
}