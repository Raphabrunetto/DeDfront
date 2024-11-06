package com.example.dedfront

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.dedfront.data.entities.CharacterDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// é um BroadcastReceiver que responde a eventos de exclusão de personagens.

class CharacterDeleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val characterId = intent.getIntExtra("character_id", -1)

        if (characterId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val characterDao = CharacterDatabase.getDatabase(context).characterDao()
                characterDao.deleteCharacter(characterId)
                Log.d("CharacterDeleteReceiver", "Personagem com ID $characterId excluído.")
            }

            // Cancelar a notificação
            val notificationId = intent.getIntExtra("notification_id", 0)
            NotificationManagerCompat.from(context).cancel(notificationId)
        } else {
            Log.e("CharacterDeleteReceiver", "ID do personagem inválido para exclusão.")
        }
    }
}
