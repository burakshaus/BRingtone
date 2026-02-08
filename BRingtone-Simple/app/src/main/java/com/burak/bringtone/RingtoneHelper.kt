package com.burak.bringtone

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.io.File
import java.io.FileInputStream

object RingtoneHelper {

    fun setRingtone(context: Context, audioUri: Uri) {
        // Check if we have permission to write settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(context)) {
                showWriteSettingsDialog(context)
                return
            }
        }

        try {
            // Copy file to ringtones directory
            val ringtoneUri = saveToRingtones(context, audioUri)
            
            if (ringtoneUri != null) {
                // Set as ringtone
                RingtoneManager.setActualDefaultRingtoneUri(
                    context,
                    RingtoneManager.TYPE_RINGTONE,
                    ringtoneUri
                )
                Toast.makeText(
                    context,
                    context.getString(R.string.ringtone_set_success),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.ringtone_set_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(
                context,
                context.getString(R.string.ringtone_set_failed),
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }

    private fun saveToRingtones(context: Context, sourceUri: Uri): Uri? {
        return try {
            val fileName = getFileName(context, sourceUri)
            val mimeType = context.contentResolver.getType(sourceUri) ?: "audio/*"

            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.Audio.Media.IS_RINGTONE, true)
                put(MediaStore.Audio.Media.IS_NOTIFICATION, false)
                put(MediaStore.Audio.Media.IS_ALARM, false)
                put(MediaStore.Audio.Media.IS_MUSIC, false)
            }

            val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, "Ringtones/")
                context.contentResolver.insert(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    values
                )
            } else {
                // For older Android versions
                val ringtonesDir = File(
                    android.os.Environment.getExternalStoragePublicDirectory(
                        android.os.Environment.DIRECTORY_RINGTONES
                    ), ""
                )
                if (!ringtonesDir.exists()) {
                    ringtonesDir.mkdirs()
                }
                
                val destFile = File(ringtonesDir, fileName)
                context.contentResolver.openInputStream(sourceUri)?.use { input ->
                    destFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                
                Uri.fromFile(destFile)
            }

            // Copy content if URI was created
            uri?.let { targetUri ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    context.contentResolver.openOutputStream(targetUri)?.use { output ->
                        context.contentResolver.openInputStream(sourceUri)?.use { input ->
                            input.copyTo(output)
                        }
                    }
                }
            }

            uri
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getFileName(context: Context, uri: Uri): String {
        var fileName = "ringtone_${System.currentTimeMillis()}.mp3"
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = cursor.getString(nameIndex)
                }
            }
        }
        return fileName
    }

    private fun showWriteSettingsDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.permission_write_settings))
            .setMessage(context.getString(R.string.permission_write_settings))
            .setPositiveButton(context.getString(R.string.grant_permission)) { _, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                        data = Uri.parse("package:${context.packageName}")
                    }
                    context.startActivity(intent)
                }
            }
            .setNegativeButton(context.getString(R.string.cancel), null)
            .show()
    }
}
