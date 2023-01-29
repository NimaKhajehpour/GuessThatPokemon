package com.nima.guessthatpokemon.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.nima.guessthatpokemon.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCollection(
    navController: NavController
) {

//    val pokemonIdViewModel = viewModel(PokemonIDsViewModel::class.java)
//
//    val idUiState = pokemonIdViewModel.pokemonIdState
//
//    LaunchedEffect(key1 = Unit){
//        pokemonIdViewModel.loadPokemonIds()
//    }
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            when (idUiState.pokemonIdList){
//                is Resources.Loading ->
//                    LoadingDialog(text = "Loading Captured Pokemons")
//                is Resources.Success -> {
//                    val ids = mutableListOf<Int>()
//                    for (pokemonId in idUiState.pokemonIdList.data!!){
//                        ids.add(pokemonId.id)
//                    }
//
//                    val fullLoads by remember {
//                        mutableStateOf(ids.size/10)
//                    }
//
//                    val lastLoad by remember{
//                        mutableStateOf(ids.size%10)
//                    }
//
//                    var loadDone by remember {
//                        mutableStateOf(0)
//                    }
//
//                    val pokemonListViewModel: PokemonListViewModel = hiltViewModel()
//                    val pokemonList = produceState<MutableList<Pokemon?>?>(initialValue = null){
//                        if (fullLoads > 0){
//                            value = pokemonListViewModel.getPokemons(ids.subList(0, 10))
//                            loadDone++
//                        }else{
//                            value = pokemonListViewModel.getPokemons(ids)
//                            loadDone++
//                        }
//                    }.value
//
//                    if (pokemonList != null){
//                        if (pokemonList.isEmpty()){
//                            TitleImage(
//                                painter = painterResource(id = R.drawable.surprised_pikachu)
//                            )
//                            Text(text = "No Pokemons Yet",
//                                style = MaterialTheme.typography.labelLarge,
//                                modifier = Modifier.padding(vertical = 8.dp)
//                            )
//                            Text(text = "Maybe You Should Guess The Pokemons Better",
//                                style = MaterialTheme.typography.labelSmall,
//                                modifier = Modifier.padding(vertical = 8.dp)
//                            )
//                        }else{
//                            Box(
//                                modifier = Modifier.fillMaxSize(),
//                            ) {
//
//                                LazyVerticalGrid(columns =GridCells.Fixed(2),
//                                    modifier = Modifier.align(Alignment.TopCenter)
//                                ){
//                                    itemsIndexed(pokemonList){ index, item ->
//                                        ElevatedCard(
//                                            modifier = Modifier
//                                                .padding(8.dp)
//                                                .fillMaxWidth(),
//                                            shape = RoundedCornerShape(10.dp),
//                                            onClick = {
//                                                //Clicked
//                                                navController.navigate(PokemonScreens.DetailScreen.name+"/${item?.id}")
//                                            }
//                                        ) {
//                                            Column(
//                                                modifier = Modifier
//                                                    .fillMaxWidth()
//                                                    .padding(8.dp),
//                                                verticalArrangement = Arrangement.Top,
//                                                horizontalAlignment = Alignment.CenterHorizontally
//                                            ) {
//                                                OnlineImageView(
//                                                    colorFilter = null,
//                                                    modifier = Modifier.padding(bottom = 4.dp),
//                                                    imageLink = "https://raw.githubusercontent.com/PokeAPI/" +
//                                                            "sprites/master/sprites/pokemon/other/" +
//                                                            "official-artwork/${item!!.id}.png"
//                                                )
//                                                Text(text = item.name.capitalize(),
//                                                    style = MaterialTheme.typography.bodySmall,
//                                                    modifier = Modifier.padding(4.dp)
//                                                )
//                                                when (item.types.size) {
//                                                    1 -> {
//                                                        Badge(
//                                                            containerColor = Constants.getTypeColor(item.types[0].type.name),
//                                                            contentColor = Color.White
//                                                        ){
//                                                            Text(text = item.types[0].type.name,
//                                                                style = MaterialTheme.typography.labelSmall,
//                                                                modifier = Modifier.padding(4.dp)
//                                                            )
//                                                        }
//                                                    }
//                                                    2 -> {
//                                                        Row(
//                                                            modifier = Modifier.fillMaxWidth(),
//                                                            verticalAlignment = Alignment.CenterVertically,
//                                                            horizontalArrangement = Arrangement.Center
//                                                        ) {
//                                                            Badge(
//                                                                modifier = Modifier.padding(4.dp),
//                                                                containerColor = Constants.getTypeColor(item.types[0].type.name),
//                                                                contentColor = Color.White
//                                                            ){
//                                                                Text(text = item.types[0].type.name,
//                                                                    style = MaterialTheme.typography.labelSmall,
//                                                                    modifier = Modifier.padding(4.dp)
//                                                                )
//                                                            }
//                                                            Badge(
//                                                                containerColor = Constants.getTypeColor(item.types[0].type.name),
//                                                                contentColor = Color.White
//                                                            ){
//                                                                Text(text = item.types[1].type.name,
//                                                                    style = MaterialTheme.typography.labelSmall,
//                                                                    modifier = Modifier.padding(4.dp)
//                                                                )
//                                                            }
//                                                        }
//                                                    }
//                                                    else -> {
//                                                        Row(
//                                                            modifier = Modifier.fillMaxWidth(),
//                                                            verticalAlignment = Alignment.CenterVertically,
//                                                            horizontalArrangement = Arrangement.Center
//                                                        ) {
//                                                            Badge(
//                                                                modifier = Modifier.padding(4.dp),
//                                                                containerColor = Constants.getTypeColor(item.types[0].type.name),
//                                                                contentColor = Color.White
//                                                            ){
//                                                                Text(text = item.types[0].type.name,
//                                                                    style = MaterialTheme.typography.labelSmall,
//                                                                    modifier = Modifier.padding(4.dp)
//                                                                )
//                                                            }
//                                                            Badge(
//                                                                containerColor = Constants.getTypeColor(item.types[0].type.name),
//                                                                contentColor = Color.White
//                                                            ){
//                                                                Text(text = item.types[1].type.name,
//                                                                    style = MaterialTheme.typography.labelSmall,
//                                                                    modifier = Modifier.padding(4.dp)
//                                                                )
//                                                            }
//                                                        }
//                                                        Row(
//                                                            modifier = Modifier.fillMaxWidth(),
//                                                            verticalAlignment = Alignment.CenterVertically,
//                                                            horizontalArrangement = Arrangement.Center
//                                                        ) {
//                                                            Badge(
//                                                                containerColor = Constants.getTypeColor(item.types[0].type.name),
//                                                                contentColor = Color.White
//                                                            ){
//                                                                Text(text = item.types[2].type.name,
//                                                                    style = MaterialTheme.typography.labelSmall,
//                                                                    modifier = Modifier.padding(4.dp)
//                                                                )
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//
//                                ButtonComponent(
//                                    modifier = Modifier
//                                        .padding(horizontal = 32.dp, vertical = 16.dp)
//                                        .align(Alignment.BottomCenter),
//                                    enabled = loadDone != fullLoads+1,
//                                    text = "Load More",
//                                    shape = null,
//                                    colors = ButtonDefaults.buttonColors(
//                                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
//                                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
//                                    )
//                                ){
//                                    runBlocking(Dispatchers.IO) {
//                                        if (loadDone < fullLoads+1){
//                                            pokemonList += if (loadDone < fullLoads){
//                                                loadDone++
//                                                pokemonListViewModel.getPokemons(ids.subList(loadDone*10-10,loadDone*10))
//                                            }else{
//                                                loadDone++
//                                                pokemonListViewModel.getPokemons(ids.subList(fullLoads*10, fullLoads*10+lastLoad))
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }else{
//                        LoadingDialog(text = "Loading Pokemon Data")
//                    }
//
//                }else -> {
//                    //error accured
//                    ErrorDialog {
//                        navController.popBackStack()
//                    }
//                }
//            }
//
//        }
//    }
}