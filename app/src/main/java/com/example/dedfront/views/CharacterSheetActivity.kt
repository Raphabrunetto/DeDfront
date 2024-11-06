package com.example.dedfront.views

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dedfront.R
import com.example.dedfront.data.entities.CharacterDatabase
import com.example.dedfront.data.entities.CharacterEntity
import com.example.dedfront.viewmodel.CharacterViewModel
import com.example.dedfront.viewmodel.CharacterViewModelFactory
import org.example.character.Attributes
import org.example.utils.Calculator

//A CharacterSheetActivity é responsável por exibir as informações detalhadas de um personagem,
// realizar a inserção inicial no banco de dados (caso o personagem ainda não exista)
// e gerenciar a exclusão de personagens.
//utiliza o Calculator para aplicar lógica de jogo, como a determinação de modificadores e pontos de vida.

class CharacterSheetActivity : AppCompatActivity() {

    private val characterViewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(CharacterDatabase.getDatabase(application).characterDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charactersheet)

        val calculator = Calculator()


        val character = createCharacterFromIntent(calculator)

        displayCharacterInfo(character, calculator)


        val characterEntity = createCharacterEntity(character, calculator.calculateHitPoints(calculator.calculateModifier(character.baseAttributes.constitution)))

        val characterId = characterEntity.id

        characterViewModel.characters.observe(this) { allCharacters ->
            // Verifica se existe algum personagem com o mesmo nome
            val characterExists = allCharacters.any { it.name == characterEntity.name }

            // Se não existe outro personagem com o mesmo nome, insere o personagem
            if (!characterExists) {
                Log.d("CharacterSheetActivity", "Chamando insertCharacter para o personagem: ${characterEntity.name}")
                characterViewModel.insertCharacter(this, characterEntity)
            } else {
                Log.d("CharacterSheetActivity", "O personagem '${characterEntity.name}' já existe na lista.")
            }
        }



        val deleteButton: Button = findViewById(R.id.deleteCharacterButton)
        deleteButton.setOnClickListener {
            if (characterId != -1) {
                Log.d("CharacterSheetActivity", "Tentando excluir o personagem com ID: $characterId")
                characterViewModel.deleteCharacter(characterId)
                finish()
            } else {
                Log.e("CharacterSheetActivity", "ID do personagem inválido para exclusão. ID: $characterId")
            }
        }

    }

    private fun createCharacterFromIntent(calculator: Calculator): Character {
        val name = intent.getStringExtra("nome") ?: "Unknown"
        val race = intent.getStringExtra("raca") ?: "Unknown"
        val characterClass = intent.getStringExtra("classe") ?: "Unknown"
        val alignment = intent.getStringExtra("alinhamento1") ?: "Unknown"
        val background = intent.getStringExtra("background") ?: "Unknown"
        val id = intent.getIntExtra("id", -1)

        // Obtém a raça e a classe usando o Calculator e valida se não são nulos
        val selectedRace = calculator.getRaceStrategy(race) ?: throw IllegalArgumentException("Race not found: $race")
        val selectedClass = calculator.getCharacterClass(characterClass) ?: throw IllegalArgumentException("Class not found: $characterClass")

        // Cria os atributos base
        val baseAttributes = Attributes(
            strength = intent.getIntExtra("forca", 0),
            dexterity = intent.getIntExtra("destreza", 0),
            constitution = intent.getIntExtra("constituicao", 0),
            intelligence = intent.getIntExtra("inteligencia", 0),
            wisdom = intent.getIntExtra("sabedoria", 0),
            charisma = intent.getIntExtra("carisma", 0)
        )

        // Use o metodo factory p instanciar
        return Character.create(id, name, selectedRace, selectedClass, alignment, background, baseAttributes)
    }

    private fun displayCharacterInfo(character: Character, calculator: Calculator) {
        findViewById<TextView>(R.id.tvCharacterName).text = "Name: ${character.name}"
        findViewById<TextView>(R.id.tvCharacterRaceClass).text = "Race: ${character.race.raceName} | Class: ${character.characterClass.className}"
        findViewById<TextView>(R.id.tvCharacterAlignment).text = "Alignment: ${character.alignment}"
        findViewById<TextView>(R.id.tvCharacterBackground).text = "Background: ${character.background}"
        findViewById<TextView>(R.id.tvStrength).text = "Strength: ${character.baseAttributes.strength} (Modifier: ${calculator.calculateModifier(character.baseAttributes.strength)})"
        findViewById<TextView>(R.id.tvDexterity).text = "Dexterity: ${character.baseAttributes.dexterity} (Modifier: ${calculator.calculateModifier(character.baseAttributes.dexterity)})"
        findViewById<TextView>(R.id.tvConstitution).text = "Constitution: ${character.baseAttributes.constitution} (Modifier: ${calculator.calculateModifier(character.baseAttributes.constitution)})"
        findViewById<TextView>(R.id.tvIntelligence).text = "Intelligence: ${character.baseAttributes.intelligence} (Modifier: ${calculator.calculateModifier(character.baseAttributes.intelligence)})"
        findViewById<TextView>(R.id.tvWisdom).text = "Wisdom: ${character.baseAttributes.wisdom} (Modifier: ${calculator.calculateModifier(character.baseAttributes.wisdom)})"
        findViewById<TextView>(R.id.tvCharisma).text = "Charisma: ${character.baseAttributes.charisma} (Modifier: ${calculator.calculateModifier(character.baseAttributes.charisma)})"
        findViewById<TextView>(R.id.tvHitPoints).text = "Hit Points: ${calculator.calculateHitPoints(calculator.calculateModifier(character.baseAttributes.constitution))}"
    }

    private fun createCharacterEntity(character: Character, hitPoints: Int): CharacterEntity {
        return CharacterEntity(
            name = character.name,
            race = character.race.raceName,
            characterClass = character.characterClass.className,
            alignment = character.alignment,
            background = character.background,
            strength = character.baseAttributes.strength,
            dexterity = character.baseAttributes.dexterity,
            constitution = character.baseAttributes.constitution,
            intelligence = character.baseAttributes.intelligence,
            wisdom = character.baseAttributes.wisdom,
            charisma = character.baseAttributes.charisma,
            hitPoints = hitPoints
        )
    }


}