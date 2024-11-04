package com.example.dedfront.views

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dedfront.adapters.CharacterAdapter
import com.example.dedfront.R
import com.example.dedfront.data.entities.CharacterDatabase
import com.example.dedfront.viewmodel.CharacterViewModel
import com.example.dedfront.viewmodel.CharacterViewModelFactory

class CharacterListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterAdapter
    private lateinit var deleteAllButton: Button // Declaração do botão

    private val characterViewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(CharacterDatabase.getDatabase(application).characterDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        // Configurações do RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observa a lista de personagens e atualiza o adapter
        characterViewModel.characters.observe(this) { allCharacters ->
            adapter = CharacterAdapter(this, allCharacters)
            recyclerView.adapter = adapter
        }

        // Configuração do botão "Deletar Todos"
        deleteAllButton = findViewById(R.id.button_delete_all)
        deleteAllButton.setOnClickListener {
            characterViewModel.deleteAllCharacters()
        }
    }
}