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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.guessthatpokemon.R
import com.nima.guessthatpokemon.components.LoadingDialog
import com.nima.guessthatpokemon.components.OnlineImageView
import com.nima.guessthatpokemon.model.PokemonAbility.PokemonAbility
import com.nima.guessthatpokemon.model.pokemon.Pokemon
import com.nima.guessthatpokemon.model.type.DoubleDamageFrom
import com.nima.guessthatpokemon.model.type.PokemonType
import com.nima.guessthatpokemon.util.Constants
import com.nima.guessthatpokemon.viewmodels.DetailScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen (
    navController: NavController,
    viewModel: DetailScreenViewModel,
    pokemonId: Int?
){

    val pokemon = produceState<Pokemon?>(initialValue = null){
        value = viewModel.getPokemonById(pokemonId!!)
    }.value

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AnimatedVisibility (pokemon == null) {
            LoadingDialog(
                text = "Loading Pokemon Details")
        }
        AnimatedVisibility(pokemon != null) {
            if (pokemon != null){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val pokemonAbilityLinks = remember {
                        mutableStateListOf<Int>()
                    }

                    val pokemonTypeLinks = remember {
                        mutableStateListOf<Int>()
                    }

                    for (ability in pokemon.abilities) {
                        pokemonAbilityLinks.add(
                            ability.ability.url.removePrefix(Constants.baseUrl + "ability/")
                                .removeSuffix("/").toInt()
                        )
                    }

                    for (type in pokemon.types) {
                        pokemonTypeLinks.add(
                            type.type.url.removePrefix(Constants.baseUrl + "type/")
                                .removeSuffix("/").toInt()
                        )
                    }

                    val pokemonAbility =
                        produceState<List<PokemonAbility>>(initialValue = emptyList()) {
                            value = viewModel.getPokemonAbilities(pokemonAbilityLinks.toSet())
                        }.value

                    val pokemonType = produceState<List<PokemonType>>(initialValue = emptyList()) {
                        value = viewModel.getPokemonTypes(pokemonTypeLinks.toSet())
                    }.value

                    AnimatedVisibility(pokemonAbility.isNotEmpty() && pokemonType.isNotEmpty()) {

                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
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
                                            text = pokemon.name.capitalize(),
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(bottom = 10.dp)
                                        )
                                        Text(
                                            text = "Height: ${pokemon.height * 10}cm",
                                            style = MaterialTheme.typography.bodySmall,
                                        )

                                        for (i in listOf(1, 3, 5)) {
                                            Text(
                                                text = "${pokemon.stats[i].stat.name.capitalize()}: ${pokemon.stats[i].base_stat}",
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
                                            text = "Weight: ${pokemon.weight / 10}kg",
                                            style = MaterialTheme.typography.bodySmall,
                                        )

                                        for (i in listOf(0, 2, 4)) {
                                            Text(
                                                text = "${pokemon.stats[i].stat.name.capitalize()}: ${pokemon.stats[i].base_stat}",
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
                                    containerColor = animateColorAsState(targetValue =
                                                        if (abilityDetails) MaterialTheme.colorScheme.tertiaryContainer
                                                        else MaterialTheme.colorScheme.surface).value,
                                    contentColor = animateColorAsState(targetValue =
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

                                AnimatedVisibility (!abilityDetails) {
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
                                            pokemonAbility.size / 2 + pokemonAbility.size % 2

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
                                                        text = pokemonAbility[(i * 2) - 2].name.capitalize(),
                                                        style = MaterialTheme.typography.bodySmall,
                                                        modifier = Modifier.padding(end = 5.dp)
                                                    )
                                                    IconButton(
                                                        onClick = {
                                                            selectedIndex = i * 2 - 2
                                                            abilityText =
                                                                pokemonAbility[selectedIndex].flavor_text_entries.filter {
                                                                    it.language.name == "en"
                                                                }[0].flavor_text ?: "No Description"
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
                                                if (2 * i - 1 <= pokemonAbility.size - 1) {
                                                    Row(
                                                        horizontalArrangement = Arrangement.Center,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Text(
                                                            text = pokemonAbility[i * 2 - 1].name.capitalize(),
                                                            style = MaterialTheme.typography.bodySmall,
                                                            modifier = Modifier.padding(end = 5.dp)
                                                        )
                                                        IconButton(
                                                            onClick = {
                                                                selectedIndex = i * 2 - 1
                                                                abilityText =
                                                                    pokemonAbility[selectedIndex].flavor_text_entries.filter {
                                                                        it.language.name == "en"
                                                                    }[0].flavor_text
                                                                        ?: "No Description"
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
                                                text = pokemonAbility[selectedIndex].name.capitalize(),
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

                            val pokemonWeakness = remember {
                                mutableStateListOf<DoubleDamageFrom>()
                            }

                            pokemonType.forEach {
                                pokemonWeakness += it.damage_relations.double_damage_from
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
                                        items(pokemonType) {
                                            Badge(
                                                modifier = Modifier.padding(5.dp),
                                                containerColor = Constants.getTypeColor(it.name),
                                                contentColor = Color.White
                                            ) {
                                                Text(
                                                    text = it.name.capitalize(),
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
                                        items(pokemonWeakness.toSet().toList()) {
                                            Badge(
                                                modifier = Modifier.padding(5.dp),
                                                containerColor = Constants.getTypeColor(it.name),
                                                contentColor = Color.White
                                            ) {
                                                Text(
                                                    text = it.name.capitalize(),
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
                    AnimatedVisibility(pokemonAbility.isEmpty() || pokemonType.isEmpty()) {
                        CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
                    }
                }
            }
        }
    }
}