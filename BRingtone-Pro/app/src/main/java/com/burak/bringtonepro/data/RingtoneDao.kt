package com.burak.bringtonepro.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RingtoneDao {
    
    @Query("SELECT * FROM ringtones ORDER BY dateAdded DESC")
    fun getAllRingtones(): LiveData<List<RingtoneEntity>>
    
    @Query("SELECT * FROM ringtones WHERE id = :id")
    suspend fun getRingtoneById(id: Long): RingtoneEntity?
    
    @Query("SELECT * FROM ringtones WHERE category = :category ORDER BY dateAdded DESC")
    fun getRingtonesByCategory(category: String): LiveData<List<RingtoneEntity>>
    
    @Query("SELECT * FROM ringtones WHERE name LIKE '%' || :query || '%' ORDER BY dateAdded DESC")
    fun searchRingtones(query: String): LiveData<List<RingtoneEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRingtone(ringtone: RingtoneEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRingtones(ringtones: List<RingtoneEntity>)
    
    @Update
    suspend fun updateRingtone(ringtone: RingtoneEntity)
    
    @Delete
    suspend fun deleteRingtone(ringtone: RingtoneEntity)
    
    @Query("DELETE FROM ringtones WHERE id = :id")
    suspend fun deleteRingtoneById(id: Long)
    
    @Query("DELETE FROM ringtones")
    suspend fun deleteAllRingtones()
    
    @Query("SELECT COUNT(*) FROM ringtones")
    suspend fun getRingtoneCount(): Int
}
