package com.rcacao.marvelchallenge.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT id FROM Character order by id")
    fun getIds(): List<String>

    @Insert
    fun insert(character: Character)

    @Query("DELETE FROM character where id = :id")
    fun deleteById(id: String)
}