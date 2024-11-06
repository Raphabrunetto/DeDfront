package com.example.dedfront.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dedfront.data.entities.CharacterDao

//Factory responsável por criar instâncias da CharacterViewModel

class CharacterViewModelFactory(private val characterDao: CharacterDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterViewModel(characterDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
