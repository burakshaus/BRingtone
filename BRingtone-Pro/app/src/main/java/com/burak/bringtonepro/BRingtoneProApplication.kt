package com.burak.bringtonepro

import android.app.Application
import com.burak.bringtonepro.data.AppDatabase
import com.burak.bringtonepro.repository.RingtoneRepository

class BRingtoneProApplication : Application() {
    
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { RingtoneRepository(database.ringtoneDao()) }
    
    override fun onCreate() {
        super.onCreate()
    }
}
