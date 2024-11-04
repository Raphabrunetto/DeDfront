package com.example.dedfront.data.entities

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity): Long

    @Query("SELECT * FROM character_table ORDER BY id ASC")
    fun getAllCharacters(): LiveData<List<CharacterEntity>>

    @Query("SELECT * FROM character_table WHERE id = :characterId")
    suspend fun getCharacterById(characterId: Int): CharacterEntity?

    @Query("DELETE FROM character_table WHERE id = :characterId")
    suspend fun deleteCharacter(characterId: Int): Int

    @Query("DELETE FROM character_table")
    suspend fun deleteAllCharacters(): Int
}
