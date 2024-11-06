package com.example.dedfront.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.dedfront.R
import com.example.dedfront.data.entities.CharacterEntity
import com.example.dedfront.views.CharacterSheetActivity

//mudar o personagem de tela

class CharacterAdapter(private val context: Context, private val characters: List<CharacterEntity>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

        //serve para associar cada item da lista ao layout que vai ser exibido na tela.

    class CharacterViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {
        private val buttonCharacter: Button = itemView.findViewById(R.id.buttonCharacter)

        fun bind(character: CharacterEntity) {
            buttonCharacter.text = character.name  // Exibe o nome do personagem
            buttonCharacter.setOnClickListener {
                val intent = Intent(context, CharacterSheetActivity::class.java).apply {
                    putExtra("id", character.id)
                    putExtra("nome", character.name)
                    putExtra("raca", character.race)
                    putExtra("classe", character.characterClass)
                    putExtra("alinhamento1", character.alignment)
                    putExtra("background", character.background)
                    putExtra("forca", character.strength)
                    putExtra("destreza", character.dexterity)
                    putExtra("constituicao", character.constitution)
                    putExtra("inteligencia", character.intelligence)
                    putExtra("sabedoria", character.wisdom)
                    putExtra("carisma", character.charisma)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_list_item, parent, false)
        return CharacterViewHolder(view, context)  // Passa o contexto para o ViewHolder
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)  // Chama a função bind
    }

    override fun getItemCount(): Int {
        return characters.size
    }
}
