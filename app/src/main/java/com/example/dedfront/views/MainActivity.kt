package com.example.dedfront.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.dedfront.R
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonToCreateCharacter = findViewById<Button>(R.id.buttonCreateCharacter)
        buttonToCreateCharacter.setOnClickListener {
            val intent = Intent(this, CharacterFormActivity::class.java)
            startActivity(intent)
        }

        val buttonToCharacterList = findViewById<Button>(R.id.buttonShowCharacterList)
        buttonToCharacterList.setOnClickListener {
            val intent = Intent(this, CharacterListActivity::class.java)
            startActivity(intent)
        }
    }
}
