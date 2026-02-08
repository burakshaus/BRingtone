package com.burak.bringtone

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var selectAudioButton: Button
    private lateinit var selectedFileText: TextView
    private var selectedAudioUri: Uri? = null

    // Audio file picker launcher
    private val audioPickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedAudioUri = it
            selectedFileText.text = getFileName(it)
            selectedFileText.visibility = TextView.VISIBLE
            
            // Navigate to AudioPlayerActivity
            val intent = Intent(this, AudioPlayerActivity::class.java).apply {
                putExtra("AUDIO_URI", it.toString())
            }
            startActivity(intent)
        }
    }

    // Permission request launcher
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openAudioPicker()
        } else {
            showPermissionDeniedDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectAudioButton = findViewById(R.id.selectAudioButton)
        selectedFileText = findViewById(R.id.selectedFileText)

        selectAudioButton.setOnClickListener {
            checkPermissionAndPickAudio()
        }
    }

    private fun checkPermissionAndPickAudio() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                openAudioPicker()
            }
            shouldShowRequestPermissionRationale(permission) -> {
                showPermissionRationaleDialog(permission)
            }
            else -> {
                permissionLauncher.launch(permission)
            }
        }
    }

    private fun openAudioPicker() {
        audioPickerLauncher.launch("audio/*")
    }

    private fun showPermissionRationaleDialog(permission: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.permission_required)
            .setMessage(R.string.permission_required)
            .setPositiveButton(R.string.grant_permission) { _, _ ->
                permissionLauncher.launch(permission)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.permission_required)
            .setMessage(R.string.permission_required)
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    private fun getFileName(uri: Uri): String {
        var fileName = "Unknown"
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = cursor.getString(nameIndex)
                }
            }
        }
        return fileName
    }
}
