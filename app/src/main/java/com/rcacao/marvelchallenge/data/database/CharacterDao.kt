package com.rcacao.marvelchallenge.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT id FROM Character ORDER BY id")
    suspend fun getIds(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    @Query("DELETE FROM character where id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM Character where name LIKE :query ORDER BY name")
    suspend fun getCharacters(query: String): List<Character>

}