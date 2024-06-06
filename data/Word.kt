package com.example.tiles.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Creates table to store words with the word and category as fields.
@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val word: String,
    val category: String
)

