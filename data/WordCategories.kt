package com.example.tiles.data

// Create different categories to be selected for populating the database and populating
// cards within the game.
sealed class Category {
    object Animals : Category()
    object Sports : Category()
    object Jobs : Category()
    object Objects : Category()
    object Food : Category()
    object Countries : Category()
    object Anatomy : Category()
    object Pokemon : Category()
}
