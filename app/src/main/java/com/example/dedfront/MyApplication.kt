package com.example.dedfront

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.room.Room
import com.example.dedfront.data.entities.CharacterDatabase

//extensão da classe Application
//objetivo de configurar alguns recursos globais e necessários para o funcionamento da aplicação
//inicia junto com o app

class MyApplication : Application() {
    lateinit var database: CharacterDatabase
        private set

//inicializa a instancia do banco usando Room
    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            CharacterDatabase::class.java,
            "character_database"
        ).build()


        createNotificationChannel()
    }

//configurar um canal de notificações
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Character Creation"
            val descriptionText = "Notifications for character creation"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("character_creation_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}