package com.example.dedfront.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


//representa a entidade de dados, a estrutura dos dados que serão armazenados no banco de dados.
// Cada instância dessa classe representa um personagem.

@Entity(tableName = "character_table")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val race: String,
    val characterClass: String,
    val alignment: String,
    val background: String,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val hitPoints: Int
)
