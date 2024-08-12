package com.example.pokemon.utils

import com.example.pokemon.data.models.PokemonDto
import com.example.pokemon.data.models.PokemonListDto
import com.example.pokemon.data.models.ResultDto
import com.example.pokemon.data.models.SpritesDto
import com.example.pokemon.data.models.StatDto
import com.example.pokemon.domain.model.Ability
import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.model.PokemonList
import com.example.pokemon.domain.model.Result
import com.example.pokemon.domain.model.Sprites
import com.example.pokemon.domain.model.Stat
import com.example.pokemon.domain.model.StatX

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

/*fun PokemonDto.toPokemon(): Pokemon {
    return Pokemon(
        baseExperience = this.baseExperience,
        height = this.height,
        id = this.id,
        isDefault = this.isDefault,
        name = this.name,
        order = this.order,
        pastTypes = this.pastTypes,  // Assuming this maps directly
        sprites = this.sprites.to,
        stats = this.stats.map { it.toStat() },
        weight = this.weight
    )
}

fun SpritesDto.toSprites(): Sprites {
    return Sprites(
        backDefault = this.backDefault,
        backFemale = this.backFemale,  // Provide a default value
        backShiny = this.backShiny,
        backShinyFemale = this.backShinyFemale,  // Provide a default value
        frontDefault = this.frontDefault,
        frontFemale = this.frontFemale.toString(),  // Provide a default value
        frontShiny = this.frontShiny,
        frontShinyFemale = this.frontShinyFemale,
        other = this.other,
        versions = this.versions// Provide a default value
    )
}*/

fun StatDto.toStat(): Stat {
    return Stat(
        baseStat = this.baseStat,
        effort = this.effort,
        stats = StatX(name = this.stat.name, url = this.stat.url)
    )
}
