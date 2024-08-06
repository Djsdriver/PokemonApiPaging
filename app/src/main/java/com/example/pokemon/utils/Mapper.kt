package com.example.pokemon.utils

import com.example.pokemon.data.models.PokemonListDto
import com.example.pokemon.data.models.ResultDto
import com.example.pokemon.domain.model.PokemonList
import com.example.pokemon.domain.model.Result

fun PokemonListDto.toPokemonList(): PokemonList {
    return PokemonList(
        results = this.results.map { Result(name = it.name, url = it.url) },
        count = this.count,
        next = this.next,
        previous = this.previous
    )
}

fun ResultDto.toResult(): Result {
    return Result(
        name = this.name,
        url = this.url
    )
}