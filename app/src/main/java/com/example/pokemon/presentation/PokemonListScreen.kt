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
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemon.data.models.PokemonListDto
import com.example.pokemon.data.models.ResultDto
import com.example.pokemon.domain.model.PokemonItem
import com.example.pokemon.domain.model.Result
import com.example.pokemon.utils.toPokemonList
import com.example.pokemon.utils.toResult

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokemonList(viewModel: PokemonListScreenViewModel) {
    val pokemonList = viewModel.pokemonResult.collectAsLazyPagingItems()

    PokemonList(pokemonList = pokemonList,viewModel)



}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokemonList(pokemonList : LazyPagingItems<ResultDto>, viewModel: PokemonListScreenViewModel){
    Scaffold(modifier = Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            items(pokemonList.itemCount) {
                pokemonList[it].let { model->
                    if (model != null) {
                        PokemonItemForList(pokemonItem = model.toResult(), viewModel =viewModel )
                    }
                }

            }

            item {
                when(val state = pokemonList.loadState.refresh){
                    is LoadState.Error ->{
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

    Box(modifier = Modifier
        .size(100.dp)
        .shadow(5.dp, RoundedCornerShape(10.dp))
        .clip(RoundedCornerShape(10.dp))
        .clickable {

        }) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally),
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