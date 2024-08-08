package com.example.pokemon.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.flatMap
import androidx.paging.map
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemon.data.models.ResultDto
import com.example.pokemon.domain.model.Result


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokemonListView(viewModel: PokemonListScreenViewModel, onEvent: (PokemonListEvent) -> Unit) {
    val state = viewModel.state.collectAsState()
    val pokemonList = state.value.list.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        onEvent(PokemonListEvent.ShowPokemonListPaging(state.value.list))
    }
    PokemonList(pokemonList = pokemonList, viewModel)

    SideEffect {
        Log.d("Recomposition", "Recomposition PokemonListView")
    }


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokemonList(pokemonList: LazyPagingItems<Result>, viewModel: PokemonListScreenViewModel) {
    SideEffect {
        Log.d("Recomposition", "Recomposition PokemonList")
    }
    Scaffold(modifier = Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            items(pokemonList.itemCount,
                key = { item ->
                    pokemonList[item]?.name ?: ""
                },
                contentType = {
                    pokemonList[it]?.name
                }) {
                pokemonList[it].let { model ->
                    if (model != null) {
                        PokemonItemForList(pokemonItem = model, viewModel = viewModel)
                    }
                }

            }

            item {
                when (val state = pokemonList.loadState.refresh) {
                    is LoadState.Error -> {
                        state.error
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
fun PokemonItemForList(pokemonItem: Result, viewModel: PokemonListScreenViewModel) {
    val imageUrl = viewModel.getPokemonImageUrl(pokemonItem)
    SideEffect {
        Log.d("Recomposition", "Recomposition PokemonItemForList")
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable {

            }
            .fillMaxSize(), // Заполняем Box
        contentAlignment = Alignment.Center // Выравниваем содержимое по центру
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally // Выравниваем содержимое внутри Column по центру
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = pokemonItem.name
            )
            Text(text = pokemonItem.name, fontSize = 12.sp)
        }
    }
}
