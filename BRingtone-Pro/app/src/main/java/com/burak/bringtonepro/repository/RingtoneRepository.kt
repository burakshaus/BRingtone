package com.burak.bringtonepro.repository

import androidx.lifecycle.LiveData
import com.burak.bringtonepro.data.RingtoneDao
import com.burak.bringtonepro.data.RingtoneEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RingtoneRepository(private val ringtoneDao: RingtoneDao) {
    
    val allRingtones: LiveData<List<RingtoneEntity>> = ringtoneDao.getAllRingtones()
    
    suspend fun getRingtoneById(id: Long): RingtoneEntity? {
        return withContext(Dispatchers.IO) {
            ringtoneDao.getRingtoneById(id)
        }
    }
    
    fun getRingtonesByCategory(category: String): LiveData<List<RingtoneEntity>> {
        return ringtoneDao.getRingtonesByCategory(category)
    }
    
    fun searchRingtones(query: String): LiveData<List<RingtoneEntity>> {
        return ringtoneDao.searchRingtones(query)
    }
    
    suspend fun insertRingtone(ringtone: RingtoneEntity): Long {
        return withContext(Dispatchers.IO) {
            ringtoneDao.insertRingtone(ringtone)
        }
    }
    
    suspend fun updateRingtone(ringtone: RingtoneEntity) {
        withContext(Dispatchers.IO) {
            ringtoneDao.updateRingtone(ringtone)
        }
    }
    
    suspend fun deleteRingtone(ringtone: RingtoneEntity) {
        withContext(Dispatchers.IO) {
            ringtoneDao.deleteRingtone(ringtone)
        }
    }
    
    suspend fun deleteRingtoneById(id: Long) {
        withContext(Dispatchers.IO) {
            ringtoneDao.deleteRingtoneById(id)
        }
    }
    
    suspend fun getRingtoneCount(): Int {
        return withContext(Dispatchers.IO) {
            ringtoneDao.getRingtoneCount()
        }
    }
}
