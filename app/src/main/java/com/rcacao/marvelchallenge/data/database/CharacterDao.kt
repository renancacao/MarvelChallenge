package com.rcacao.marvelchallenge.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT id FROM Character order by id")
    suspend fun getIds(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    @Query("DELETE FROM character where id = :id")
    suspend fun deleteById(id: String)
}