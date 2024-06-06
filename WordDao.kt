package com.example.tiles.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {

    // Get a random category from the database.
    @Query("SELECT DISTINCT category FROM word_table ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomCategory(): String?

    // Get a random word from the randomly selected category.
    @Query("SELECT word FROM word_table WHERE category = :category ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomWord(category: String): String?

    // Insert words into the database based on the list of words selected.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWords(words: List<Word>)

    // Used to clear database between instances so that words are not added multiple times.
    @Query("DELETE FROM word_table")
    suspend fun deleteAllWords()

}




