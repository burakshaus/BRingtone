package com.burak.bringtonepro.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ringtones")
data class RingtoneEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val name: String,
    val uri: String,
    val duration: Long, // in milliseconds
    val dateAdded: Long, // timestamp
    val isCustom: Boolean = true,
    val category: String = "Custom", // Custom, System, Downloaded
    val filePath: String? = null,
    val fileSize: Long = 0, // in bytes
    val format: String = "mp3" // mp3, wav, aac, flac
)
