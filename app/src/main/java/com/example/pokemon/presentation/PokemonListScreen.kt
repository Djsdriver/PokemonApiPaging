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
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.example.pokemon.domain.PokemonDetails
import com.example.pokemon.domain.model.Result


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokemonListView(
    onClick: (PokemonDetails) -> Unit,
    viewModel: PokemonListScreenViewModel,
    onEvent: (PokemonListEvent) -> Unit
) {
    val state = viewModel.state.collectAsState()
    val pokemonList = state.value.list.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        onEvent(PokemonListEvent.ShowPokemonListPaging(state.value.list))
    }
    PokemonList(
        pokemonList = pokemonList,
        viewModel = viewModel,
        onClick = onClick)

    SideEffect {
        Log.d("Recomposition", "Recomposition PokemonListView")
    }


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokemonList(
    onClick: (PokemonDetails) -> Unit,
    pokemonList: LazyPagingItems<Result>,
    viewModel: PokemonListScreenViewModel
) {
    SideEffect {
        Log.d("Recomposition", "Recomposition PokemonList")
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        // Проверяем состояние загрузки
        if (pokemonList.itemCount == 0) {
            // Если данные еще не загружены, отображаем индикатор загрузки
            CircularProgressIndicator()
            return@Scaffold // Пропускаем дальнейший рендеринг
        }
        var count = 0

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            items(pokemonList.itemCount,
                key = { index ->
                    pokemonList[index]?.name ?: ""
                }
            ) { index ->
                pokemonList[index]?.let { model ->
                    PokemonItemForList(pokemonItem = model, viewModel = viewModel, onClick = {
                        onClick(
                            PokemonDetails(model.name)
                        )
                    })
                    Log.d("Model${count++}", "${model}")
                }
            }

            // Обработка состояния загрузки или ошибки
            item {
                when (val state = pokemonList.loadState.refresh) {
                    is LoadState.Error -> {
                        Text(text = "Error: ${state.error.localizedMessage}", color = Color.Red)
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
@NonRestartableComposable
fun PokemonItemForList(
    onClick: () -> Unit,
    pokemonItem: Result,
    viewModel: PokemonListScreenViewModel
) {
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

