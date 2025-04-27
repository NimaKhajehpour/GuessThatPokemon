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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.nima.guessthatpokemon.PokemonForGameQuery
import com.nima.guessthatpokemon.R
import com.nima.guessthatpokemon.client.apolloClient
import com.nima.guessthatpokemon.components.*
import com.nima.guessthatpokemon.type.*
import com.nima.guessthatpokemon.util.Constants
import com.nima.guessthatpokemon.viewmodels.PokemonGameViewModel
import kotlinx.coroutines.launch
import java.security.cert.PKIXRevocationChecker.Option

@Composable
fun GameScreen(
    navController: NavController,
    viewModel: PokemonGameViewModel,
    lang: String?,
    gen: String?
){

    val context = LocalContext.current
    val randomIndex = remember{ mutableStateOf(viewModel.pokemonIndex?.value) }

    val generation: String? by remember {
       mutableStateOf(
           when(gen){
               "1" -> "generation-i"
               "2" -> "generation-ii"
               "3" -> "generation-iii"
               "4" -> "generation-iv"
               "5" -> "generation-v"
               "6" -> "generation-vi"
               "7" -> "generation-vii"
               "8" -> "generation-viii"
               "9" -> "generation-ix"
               else -> null
           }
       )
    }

    if (randomIndex.value?.size == 20){

        var pokemonList: ApolloResponse<PokemonForGameQuery.Data>? by remember {
            mutableStateOf(null)
        }

        LaunchedEffect(key1 = Unit){
            launch {
                pokemonList = apolloClient.query(PokemonForGameQuery(
                    ids = Optional.present(Constants.giveRandomIds(generation, lang!!)),
                    generation = if (generation != null) Optional.present(generation) else Optional.present(""),
                    language = Optional.present(lang)
                )).execute()
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility (pokemonList == null || pokemonList?.data == null){
                    LoadingDialog()
                }
                AnimatedVisibility (pokemonList != null && pokemonList?.data?.pokemon_v2_pokemonspecies!!.isEmpty()){
                    // Could not connect to api
                    ErrorDialog{
                        navController.popBackStack()
                    }
                }
                AnimatedVisibility( pokemonList != null && pokemonList?.data?.pokemon_v2_pokemonspecies?.size!! > 0){
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
                            pokemonList!!.data?.pokemon_v2_pokemonspecies!![pokemonListIndex].pokemon_v2_pokemonspeciesnames[0].name

                        Log.d("LOL", "GameScreen: $pokemonName")

                        val pokemonImageLink =
                            "https://raw.githubusercontent.com/PokeAPI/" +
                                    "sprites/master/sprites/pokemon/other/" +
                                    "official-artwork/${pokemonList!!.data?.pokemon_v2_pokemonspecies!![pokemonListIndex].id}.png"

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
                            Text(text = stringResource(R.string.progressOf_20, progress),
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
                                .padding(start = 32.dp, end = 32.dp, bottom = 32.dp, top = 16.dp),
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
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp, end = 8.dp),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextButtonComponent(
                                        text = stringResource(R.string.skip),
                                        style = MaterialTheme.typography.bodyMedium)
                                    {
                                        chances = 3
                                        goNext = true
                                        pokemonImageTint.value = null
                                    }
                                }
                                OnlineImageView(
                                    colorFilter = pokemonImageTint.value,
                                    modifier = Modifier.padding(top = 8.dp, start = 32.dp, end = 32.dp, bottom = 32.dp),
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
                                    Text(text = pokemonName,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    )
                                    textFieldEnabled = false
                                }
                                Text(text = stringResource(R.string.guess_that_pokemon),
                                    style = MaterialTheme.typography.titleLarge,
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
                                        Text(text = stringResource(R.string.pokemon_name))
                                    }
                                ){
                                    textFieldValue = it
                                }

                                ButtonComponent(
                                    modifier = Modifier
                                        .padding(bottom = 8.dp, top = 16.dp)
                                        .fillMaxWidth(),
                                    text = if (goNext || chances == 3) stringResource(R.string.next) else stringResource(
                                        R.string.submit
                                    ),
                                    shape = AbsoluteRoundedCornerShape(
                                        bottomLeft = 15.dp, bottomRight = 10.dp
                                    ),
                                    enabled = textFieldValue.isNotBlank() || goNext || chances == 3
                                ){
                                    // game logic
                                    if (!goNext){
                                        if (textFieldValue.isNotBlank()){
                                            if (chances < 3) {
                                                if (textFieldValue.trim()
                                                        .equals(pokemonName, ignoreCase = true)
                                                ) {
                                                    Toast.makeText(context,
                                                        context.getString(R.string.pokemon_captured), Toast.LENGTH_SHORT)
                                                        .show()
                                                    val pokemon =
                                                        com.nima.guessthatpokemon.model.Pokemon(pokemonList?.data?.pokemon_v2_pokemonspecies!![pokemonListIndex].id)

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