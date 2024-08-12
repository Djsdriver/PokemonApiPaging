package com.example.pokemon.utils

import androidx.compose.ui.graphics.Color
import com.example.pokemon.domain.model.Stat
import com.example.pokemon.domain.model.Type
import java.util.*

fun parseTypeToColor(type: Type): Color {
    return when(type.type.name.toLowerCase(Locale.ROOT)) {
        "normal" -> Color.Red
        "fire" -> Color.Red
        "water" -> Color.Red
        "electric" -> Color.Red
        "grass" -> Color.Red
        "ice" -> Color.Red
        "fighting" -> Color.Red
        "poison" -> Color.Red
        "ground" -> Color.Red
        "flying" -> Color.Red
        "psychic" -> Color.Red
        "bug" -> Color.Red
        "rock" -> Color.Red
        "ghost" -> Color.Red
        "dragon" -> Color.Red
        "dark" -> Color.Red
        "steel" -> Color.Red
        "fairy" -> Color.Red
        else -> Color.Black
    }
}

fun parseStatToColor(stat: com.example.pokemon.data.testclass.Stat): Color {
    return when(stat.stat.name.toLowerCase()) {
        "hp" -> Color.Red
        "attack" -> Color.Red
        "defense" -> Color.Red
        "special-attack" -> Color.Red
        "special-defense" -> Color.Red
        "speed" -> Color.Red
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: com.example.pokemon.data.testclass.Stat): String {
    return when(stat.stat.name.toLowerCase()) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}