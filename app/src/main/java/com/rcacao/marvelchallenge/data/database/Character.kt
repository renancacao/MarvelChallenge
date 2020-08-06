package com.rcacao.marvelchallenge.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey val id: String,
    val name: String,
    val imageListUrl: String,
    val imageDetailUrl: String,
    val description: String
)
