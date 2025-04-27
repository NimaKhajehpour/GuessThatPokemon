package com.nima.guessthatpokemon.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.nima.guessthatpokemon.PokemonByIdQuery
import com.nima.guessthatpokemon.R
import com.nima.guessthatpokemon.TypeWeaknessQuery
import com.nima.guessthatpokemon.client.apolloClient
import com.nima.guessthatpokemon.components.LoadingDialog
import com.nima.guessthatpokemon.components.OnlineImageView
import com.nima.guessthatpokemon.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen (
    navController: NavController,
    pokemonId: Int?
){

    val context = LocalContext.current

    var pokemon: ApolloResponse<PokemonByIdQuery.Data>? by remember { mutableStateOf(null) }
    var pokemonWeakness: ApolloResponse<TypeWeaknessQuery.Data>? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(pokemonId != null) {
        if (pokemonId != null){
            pokemon = apolloClient.query(
                PokemonByIdQuery(
                    id = Optional.present(pokemonId!!)
                )
            ).execute()
        }
    }

    LaunchedEffect(pokemon != null && pokemon!!.data != null) {
        if (pokemon != null && pokemon!!.data != null) {
            pokemonWeakness = apolloClient.query(
                TypeWeaknessQuery(
                    _in = Optional.present(pokemon!!.data!!.pokemon_v2_pokemon[0].pokemon_v2_pokemontypes.map {
                        it.pokemon_v2_type!!.id
                    })
                )
            ).execute()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AnimatedVisibility (pokemon == null) {
            LoadingDialog(
                text = stringResource(R.string.loading_pokemon_details)
            )
        }
        AnimatedVisibility(pokemon != null && pokemonWeakness != null) {
                if (pokemon != null && pokemonWeakness != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        AnimatedVisibility(pokemon != null && pokemonWeakness != null) {

                            Column(
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                OnlineImageView(
                                    colorFilter = null,
                                    imageLink = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png",
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )

                                OutlinedCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    shape = RoundedCornerShape(15.dp),
                                    colors = CardDefaults.outlinedCardColors(
                                        contentColor = MaterialTheme.colorScheme.onTertiary,
                                        containerColor = MaterialTheme.colorScheme.tertiary
                                    )
                                ) {

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.Top,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.Top,
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(
                                                text = pokemon!!.data!!.pokemon_v2_pokemon[0].pokemon_v2_pokemonspecy!!.
                                                pokemon_v2_pokemonspeciesnames[0]!!.name.capitalize(),
                                                style = MaterialTheme.typography.bodyMedium,
                                                modifier = Modifier.padding(bottom = 10.dp)
                                            )
                                            Text(
                                                text = stringResource(
                                                    R.string.height_cm,
                                                    pokemon!!.data!!.pokemon_v2_pokemon[0].height!! * 10
                                                ),
                                                style = MaterialTheme.typography.bodySmall,
                                            )

                                            for (i in listOf(1, 3, 5)) {
                                                Text(
                                                    text = "${pokemon!!.data!!.pokemon_v2_pokemon[0]
                                                        .pokemon_v2_pokemonstats[i].pokemon_v2_stat!!.name.capitalize()}: " +
                                                            "${pokemon!!.data!!.pokemon_v2_pokemon[0]
                                                                .pokemon_v2_pokemonstats[i].base_stat}",
                                                    style = MaterialTheme.typography.bodySmall,
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.weight(1f))
                                        Column(
                                            verticalArrangement = Arrangement.Top,
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(
                                                text = "",
                                                style = MaterialTheme.typography.bodyMedium,
                                                modifier = Modifier.padding(bottom = 10.dp)
                                            )

                                            Text(
                                                text = stringResource(
                                                    R.string.weight_kg,
                                                    pokemon!!.data!!.pokemon_v2_pokemon[0].weight!! / 10
                                                ),
                                                style = MaterialTheme.typography.bodySmall,
                                            )

                                            for (i in listOf(0, 2, 4)) {
                                                Text(
                                                    text = "${pokemon!!.data!!.pokemon_v2_pokemon[0]
                                                        .pokemon_v2_pokemonstats[i].pokemon_v2_stat!!.name.capitalize()}: " +
                                                            "${pokemon!!.data!!.pokemon_v2_pokemon[0]
                                                                .pokemon_v2_pokemonstats[i].base_stat}",
                                                    style = MaterialTheme.typography.bodySmall,
                                                )
                                            }
                                        }
                                    }

                                }

                                var abilityDetails by remember {
                                    mutableStateOf(false)
                                }

                                OutlinedCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    shape = RoundedCornerShape(15.dp),
                                    colors = CardDefaults.outlinedCardColors(
                                        containerColor = animateColorAsState(
                                            targetValue =
                                            if (abilityDetails) MaterialTheme.colorScheme.tertiaryContainer
                                            else MaterialTheme.colorScheme.surface
                                        ).value,
                                        contentColor = animateColorAsState(
                                            targetValue =
                                            if (abilityDetails) MaterialTheme.colorScheme.onTertiaryContainer
                                            else MaterialTheme.colorScheme.onSurface
                                        ).value
                                    )
                                ) {

                                    var selectedIndex by remember {
                                        mutableStateOf(0)
                                    }

                                    var abilityText by remember {
                                        mutableStateOf("")
                                    }

                                    AnimatedVisibility(!abilityDetails) {
                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth(),
                                            verticalArrangement = Arrangement.Top,
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(
                                                text = "Abilities",
                                                style = MaterialTheme.typography.bodyMedium,
                                                modifier = Modifier.padding(bottom = 8.dp)
                                            )

                                            val rowsNeeded =
                                                pokemon!!.data!!.pokemon_v2_pokemon[0]!!.pokemon_v2_pokemonabilities.size / 2 +
                                                        pokemon!!.data!!.pokemon_v2_pokemon[0]!!.pokemon_v2_pokemonabilities.size % 2

                                            for (i in 1..rowsNeeded) {
                                                Row(
                                                    modifier = Modifier
                                                        .padding(
                                                            bottom = 8.dp,
                                                            start = 32.dp,
                                                            end = 32.dp
                                                        )
                                                        .fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Start
                                                ) {
                                                    Row(
                                                        horizontalArrangement = Arrangement.Center,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Text(
                                                            text =  pokemon!!.data!!.pokemon_v2_pokemon[0]!!
                                                                .pokemon_v2_pokemonabilities[(i * 2) - 2].pokemon_v2_ability!!.name.capitalize(),
                                                            style = MaterialTheme.typography.bodySmall,
                                                            modifier = Modifier.padding(end = 5.dp)
                                                        )
                                                        IconButton(
                                                            onClick = {
                                                                selectedIndex = i * 2 - 2
                                                                abilityText =
                                                                    pokemon!!.data!!.pokemon_v2_pokemon[0]!!
                                                                        .pokemon_v2_pokemonabilities[selectedIndex].pokemon_v2_ability!!
                                                                        .pokemon_v2_abilityflavortexts[0].flavor_text
                                                                        ?: context.getString(
                                                                            R.string.no_description
                                                                        )
                                                                abilityDetails = true
                                                            },
                                                            modifier = Modifier.size(24.dp),
                                                        ) {
                                                            Icon(
                                                                painter = painterResource(id = R.drawable.ic_twotone_help_24),
                                                                contentDescription = null
                                                            )
                                                        }
                                                    }
                                                    Spacer(modifier = Modifier.weight(1f))
                                                    if (2 * i - 1 <= pokemon!!.data!!.pokemon_v2_pokemon[0]!!
                                                            .pokemon_v2_pokemonabilities.size - 1) {
                                                        Row(
                                                            horizontalArrangement = Arrangement.Center,
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Text(
                                                                text = pokemon!!.data!!.pokemon_v2_pokemon[0]!!
                                                                    .pokemon_v2_pokemonabilities[(i * 2) - 1].pokemon_v2_ability!!.name.capitalize(),
                                                                style = MaterialTheme.typography.bodySmall,
                                                                modifier = Modifier.padding(end = 5.dp)
                                                            )
                                                            IconButton(
                                                                onClick = {
                                                                    selectedIndex = i * 2 - 1
                                                                    abilityText =
                                                                        pokemon!!.data!!.pokemon_v2_pokemon[0]!!
                                                                            .pokemon_v2_pokemonabilities[selectedIndex].pokemon_v2_ability!!
                                                                            .pokemon_v2_abilityflavortexts[0].flavor_text
                                                                            ?: context.getString(R.string.no_description)
                                                                    abilityDetails = true
                                                                },
                                                                modifier = Modifier.size(24.dp),
                                                            ) {
                                                                Icon(
                                                                    painter = painterResource(id = R.drawable.ic_twotone_help_24),
                                                                    contentDescription = null
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    AnimatedVisibility(visible = abilityDetails) {
                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth(),
                                            verticalArrangement = Arrangement.Top,
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 16.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                IconButton(
                                                    onClick = {
                                                        abilityDetails = false
                                                    },
                                                    modifier = Modifier
                                                        .padding(end = 10.dp)
                                                        .size(24.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.ArrowBack,
                                                        contentDescription = null
                                                    )
                                                }
                                                Text(
                                                    text = pokemon!!.data!!.pokemon_v2_pokemon[0]!!
                                                        .pokemon_v2_pokemonabilities[selectedIndex].pokemon_v2_ability!!.name.capitalize(),
                                                    style = MaterialTheme.typography.bodyMedium,
                                                )
                                            }
                                            Text(
                                                text = abilityText,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                }

                                OutlinedCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    shape = RoundedCornerShape(15.dp),
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            text = "Types",
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(bottom = 8.dp)
                                        )
                                        LazyRow(
                                            modifier = Modifier
                                                .padding(bottom = 8.dp)
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            items(pokemon!!.data!!.pokemon_v2_pokemon[0].pokemon_v2_pokemontypes) {
                                                Badge(
                                                    modifier = Modifier.padding(5.dp),
                                                    containerColor = Constants.getTypeColor(it.pokemon_v2_type!!.name),
                                                    contentColor = Color.White
                                                ) {
                                                    Text(
                                                        text = it.pokemon_v2_type.name.capitalize(),
                                                        style = MaterialTheme.typography.bodySmall,
                                                        modifier = Modifier.padding(5.dp)
                                                    )
                                                }
                                            }
                                        }

                                        Divider()

                                        Text(
                                            text = "Weakness",
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                                        )
                                        LazyRow(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            items(items = pokemonWeakness!!.data!!.pokemon_v2_typeefficacy.toSet().toList()) {
                                                Badge(
                                                    modifier = Modifier.padding(5.dp),
                                                    containerColor = Constants.getTypeColor(it.pokemon_v2_type!!.name),
                                                    contentColor = Color.White
                                                ) {
                                                    Text(
                                                        text = it.pokemon_v2_type!!.name.capitalize(),
                                                        style = MaterialTheme.typography.bodySmall,
                                                        modifier = Modifier.padding(5.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                        AnimatedVisibility(pokemon == null || pokemonWeakness == null) {
                            CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
                        }
                    }
                }
        }
    }
}