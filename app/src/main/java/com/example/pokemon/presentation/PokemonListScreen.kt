package com.example.pokemon.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.flatMap
import androidx.paging.map
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemon.data.models.ResultDto
import com.example.pokemon.domain.PokemonDetails
import com.example.pokemon.domain.Strong
import com.example.pokemon.domain.model.Result
import kotlinx.coroutines.flow.toList


@Composable
fun PokemonListView(
    onClick: (PokemonDetails) -> Unit,
    viewModel: PokemonListScreenViewModel = hiltViewModel<PokemonListScreenViewModel>(),
    onEvent: (PokemonListEvent) -> Unit = viewModel::onEvent
) {
    val lazyListState = rememberLazyGridState()
    val state by viewModel.state.collectAsState()
    val pokemonList = state.list.collectAsLazyPagingItems()

    //перенес получение списка в блок инит
    /*LaunchedEffect(Unit){
        onEvent(PokemonListEvent.ShowPokemonListPaging)
    }*/

    SideEffect {
        Log.d("Recomposition", "PokemonListView")
    }


    PokemonList(
        pokemonList = pokemonList,
        viewModel = viewModel,
        onClick = onClick,
        lazyGridState = lazyListState,
        onEvent = onEvent
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokemonList(
    onClick: (PokemonDetails) -> Unit,
    pokemonList: LazyPagingItems<Result>,
    viewModel: PokemonListScreenViewModel,
    lazyGridState: LazyGridState,
    onEvent: (PokemonListEvent) -> Unit
) {
    SideEffect {
        Log.d("Recomposition", "PokemonList")
    }
    Scaffold(modifier = Modifier.fillMaxSize()) {
        /*if (pokemonList.itemCount == 0) {
            CircularProgressIndicator()
            return@Scaffold
        }*/

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            state = lazyGridState
        ) {
            items(pokemonList.itemCount, key = { index ->
                pokemonList[index]?.name ?: ""
            }) { index ->
                pokemonList[index]?.let { model ->
                    PokemonItemForList(pokemonItem = model, viewModel = viewModel, onClick = {
                        onClick(PokemonDetails(model.name, Strong.VERY_STRONG))
                        Log.d("cachedPokemonList", "${model}")
                    })
                }
            }

            // Error and loading states
            item {
                when (val state = pokemonList.loadState.refresh) {
                    is LoadState.Error -> {
                        ErrorScreen(errorMessage = state.error.message?:"Some Error"){
                            onEvent(PokemonListEvent.ShowPokemonListPaging)
                        }
                    }

                    is LoadState.Loading -> {
                        CircularProgressIndicator()
                    }

                    else -> {}
                }
            }
        }
    }
}

@Composable
private fun ErrorScreen(
    errorMessage: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Error: $errorMessage", color = Color.Red)
        Button(onClick = { onClick() }) {
            Text("Попробовать снова")
        }
    }
}


@Composable
@NonRestartableComposable
fun PokemonItemForList(
    onClick: () -> Unit,
    pokemonItem: Result,
    viewModel: PokemonListScreenViewModel
) {
    SideEffect {
        Log.d("Recomposition", "PokemonItemForList")
    }

    val imageUrl = remember(pokemonItem.url) { viewModel.getPokemonImageUrl(pokemonItem) }
    val context = LocalContext.current
    val imageBuilder = remember {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build()
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageBuilder,
                contentDescription = pokemonItem.name,
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp)
            )
            Text(text = pokemonItem.name, fontSize = 12.sp)
        }
    }
}
