package com.example.myapplication.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.dedfront.R
import com.example.dedfront.views.CharacterFormActivity
import com.example.dedfront.views.CharacterListActivity
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
