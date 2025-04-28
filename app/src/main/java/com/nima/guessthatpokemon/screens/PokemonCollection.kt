package com.nima.guessthatpokemon.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.nima.guessthatpokemon.R
import com.nima.guessthatpokemon.PokemonListForCollectionQuery
import com.nima.guessthatpokemon.client.apolloClient
import com.nima.guessthatpokemon.components.*
import com.nima.guessthatpokemon.navigation.PokemonScreens
import com.nima.guessthatpokemon.util.Constants
import com.nima.guessthatpokemon.viewmodels.PokemonListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCollection(
    navController: NavController,
    viewModel: PokemonListViewModel
) {

    val context = LocalContext.current

    val pokemons = viewModel.pokemonList.collectAsState().value

    var pokemonList: ApolloResponse<PokemonListForCollectionQuery.Data>? by remember {
        mutableStateOf(null)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!pokemons.isNullOrEmpty()){
                val ids = mutableListOf<Int>()
                for (pokemonId in pokemons) {
                    ids.add(pokemonId.id)
                }

                LaunchedEffect(Unit) {
                    try{
                        pokemonList =
                            apolloClient.query(
                                PokemonListForCollectionQuery(
                                    ids = Optional.present(
                                        ids
                                    )
                                )
                            )
                                .execute()
                    }catch (e: Exception){
                        Toast.makeText(context, "Error retrieving Data", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                }

                if (pokemonList == null || pokemonList!!.data!!.pokemon_v2_pokemon.isEmpty()) {
                    TitleImage(
                        painter = painterResource(id = R.drawable.surprised_pikachu)
                    )
                    Text(
                        text = stringResource(R.string.no_pokemons_yet),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = stringResource(R.string.mocking_prompt),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var selectedGeneration by remember {
                            mutableStateOf(0)
                        }
                        LazyRow(
                            modifier = Modifier.fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            contentPadding = PaddingValues(5.dp)
                        ) {
                            items(10){
                                FilterChip(
                                    selected = it == selectedGeneration,
                                    onClick = {
                                        selectedGeneration = it
                                    },
                                    label = {
                                        Text(Constants.returnGenerationName(it))
                                    },
                                    modifier = Modifier.padding(end = 3.dp)
                                )
                            }
                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                        ) {
                            itemsIndexed(if (selectedGeneration == 0) pokemonList!!.data!!.pokemon_v2_pokemon else
                                pokemonList!!.data!!.pokemon_v2_pokemon.filter {
                                    it.pokemon_v2_pokemonspecy!!.pokemon_v2_generation!!.name == Constants.returnGenerationName(selectedGeneration)
                                }
                            ) { index, item ->
                                ElevatedCard(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(10.dp),
                                    onClick = {
                                        //Clicked
                                        navController.navigate(PokemonScreens.DetailScreen.name + "/${item.id}")
                                    }
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        OnlineImageView(
                                            colorFilter = null,
                                            modifier = Modifier.padding(bottom = 4.dp),
                                            imageLink = "https://raw.githubusercontent.com/PokeAPI/" +
                                                    "sprites/master/sprites/pokemon/other/" +
                                                    "official-artwork/${item.id}.png"
                                        )
                                        Text(
                                            text = item.name.capitalize(),
                                            style = MaterialTheme.typography.bodySmall,
                                            modifier = Modifier.padding(4.dp)
                                        )
                                        when (item.pokemon_v2_pokemontypes.size) {
                                            1 -> {
                                                Badge(
                                                    containerColor = Constants.getTypeColor(item.pokemon_v2_pokemontypes[0].pokemon_v2_type!!.name),
                                                    contentColor = Color.White
                                                ) {
                                                    Text(
                                                        text = item.pokemon_v2_pokemontypes[0].pokemon_v2_type!!.name,
                                                        style = MaterialTheme.typography.labelSmall,
                                                        modifier = Modifier.padding(4.dp)
                                                    )
                                                }
                                            }
                                            2 -> {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Badge(
                                                        modifier = Modifier.padding(4.dp),
                                                        containerColor = Constants.getTypeColor(
                                                            item.pokemon_v2_pokemontypes[0].pokemon_v2_type!!.name
                                                        ),
                                                        contentColor = Color.White
                                                    ) {
                                                        Text(
                                                            text = item.pokemon_v2_pokemontypes[0].pokemon_v2_type!!.name,
                                                            style = MaterialTheme.typography.labelSmall,
                                                            modifier = Modifier.padding(4.dp)
                                                        )
                                                    }
                                                    Badge(
                                                        containerColor = Constants.getTypeColor(
                                                            item.pokemon_v2_pokemontypes[1].pokemon_v2_type!!.name
                                                        ),
                                                        contentColor = Color.White
                                                    ) {
                                                        Text(
                                                            text = item.pokemon_v2_pokemontypes[1].pokemon_v2_type!!.name,
                                                            style = MaterialTheme.typography.labelSmall,
                                                            modifier = Modifier.padding(4.dp)
                                                        )
                                                    }
                                                }
                                            }
                                            else -> {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Badge(
                                                        modifier = Modifier.padding(4.dp),
                                                        containerColor = Constants.getTypeColor(
                                                            item.pokemon_v2_pokemontypes[0].pokemon_v2_type!!.name
                                                        ),
                                                        contentColor = Color.White
                                                    ) {
                                                        Text(
                                                            text = item.pokemon_v2_pokemontypes[0].pokemon_v2_type!!.name,
                                                            style = MaterialTheme.typography.labelSmall,
                                                            modifier = Modifier.padding(4.dp)
                                                        )
                                                    }
                                                    Badge(
                                                        containerColor = Constants.getTypeColor(
                                                            item.pokemon_v2_pokemontypes[1].pokemon_v2_type!!.name
                                                        ),
                                                        contentColor = Color.White
                                                    ) {
                                                        Text(
                                                            text = item.pokemon_v2_pokemontypes[1].pokemon_v2_type!!.name,
                                                            style = MaterialTheme.typography.labelSmall,
                                                            modifier = Modifier.padding(4.dp)
                                                        )
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Badge(
                                                        containerColor = Constants.getTypeColor(
                                                            item.pokemon_v2_pokemontypes[2].pokemon_v2_type!!.name
                                                        ),
                                                        contentColor = Color.White
                                                    ) {
                                                        Text(
                                                            text = item.pokemon_v2_pokemontypes[2].pokemon_v2_type!!.name,
                                                            style = MaterialTheme.typography.labelSmall,
                                                            modifier = Modifier.padding(4.dp)
                                                        )
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
            }else{
                TitleImage(
                    painter = painterResource(id = R.drawable.surprised_pikachu)
                )
                Text(
                    text = stringResource(R.string.no_pokemons_yet),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = stringResource(R.string.mocking_prompt),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

        }
    }
}