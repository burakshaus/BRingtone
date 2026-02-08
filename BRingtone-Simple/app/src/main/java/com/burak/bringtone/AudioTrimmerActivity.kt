package com.burak.bringtone

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

class AudioTrimmerActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var audioTitleTrimmer: TextView
    private lateinit var startSeekBar: SeekBar
    private lateinit var endSeekBar: SeekBar
    private lateinit var startTimeText: TextView
    private lateinit var endTimeText: TextView
    private lateinit var durationText: TextView
    private lateinit var previewButton: Button
    private lateinit var saveTrimmedButton: Button

    private var audioUri: Uri? = null
    private var mediaPlayer: MediaPlayer? = null
    private var totalDuration = 0
    private var startPosition = 0
    private var endPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_trimmer)

        initViews()
        setupToolbar()

        // Get audio URI from intent
        val uriString = intent.getStringExtra("AUDIO_URI")
        audioUri = Uri.parse(uriString)

        audioUri?.let {
            audioTitleTrimmer.text = getFileName(it)
            setupAudio(it)
        }

        setupListeners()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        audioTitleTrimmer = findViewById(R.id.audioTitleTrimmer)
        startSeekBar = findViewById(R.id.startSeekBar)
        endSeekBar = findViewById(R.id.endSeekBar)
        startTimeText = findViewById(R.id.startTimeText)
        endTimeText = findViewById(R.id.endTimeText)
        durationText = findViewById(R.id.durationText)
        previewButton = findViewById(R.id.previewButton)
        saveTrimmedButton = findViewById(R.id.saveTrimmedButton)
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupAudio(uri: Uri) {
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(applicationContext, uri)
                prepare()
                totalDuration = this.duration
            }

            // Initialize seekbars
            startSeekBar.max = totalDuration
            endSeekBar.max = totalDuration
            endSeekBar.progress = totalDuration
            
            startPosition = 0
            endPosition = totalDuration

            updateTimeTexts()
        } catch (e: Exception) {
            Toast.makeText(this, "Error loading audio", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun setupListeners() {
        startSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    startPosition = progress.coerceAtMost(endPosition)
                    startSeekBar.progress = startPosition
                    updateTimeTexts()
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        endSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    endPosition = progress.coerceAtLeast(startPosition)
                    endSeekBar.progress = endPosition
                    updateTimeTexts()
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        previewButton.setOnClickListener {
            previewTrimmedAudio()
        }

        saveTrimmedButton.setOnClickListener {
            saveTrimmedAudio()
        }
    }

    private fun updateTimeTexts() {
        startTimeText.text = formatTime(startPosition)
        endTimeText.text = formatTime(endPosition)
        val selectedDuration = endPosition - startPosition
        durationText.text = "Selected: ${formatTime(selectedDuration)}"
    }

    private fun previewTrimmedAudio() {
        try {
            mediaPlayer?.release()
            audioUri?.let { uri ->
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(applicationContext, uri)
                    prepare()
                    seekTo(startPosition)
                    start()

                    // Stop at end position
                    setOnCompletionListener {
                        stop()
                        reset()
                    }

                    // Manual check for end position
                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(object : Runnable {
                        override fun run() {
                            if (isPlaying && currentPosition >= endPosition) {
                                pause()
                                seekTo(startPosition)
                            } else if (isPlaying) {
                                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(this, 100)
                            }
                        }
                    }, 100)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error previewing audio", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun saveTrimmedAudio() {
        try {
            audioUri?.let { uri ->
                // Create output file
                val outputDir = getExternalFilesDir(null)
                val outputFile = File(outputDir, "trimmed_${System.currentTimeMillis()}.mp3")

                // Note: Actual audio trimming requires more complex processing
                // This is a simplified version that copies the file
                contentResolver.openInputStream(uri)?.use { input ->
                    FileOutputStream(outputFile).use { output ->
                        input.copyTo(output)
                    }
                }

                Toast.makeText(this, getString(R.string.trimmed_audio_saved), Toast.LENGTH_SHORT).show()
                
                // Offer to set as ringtone
                RingtoneHelper.setRingtone(this, Uri.fromFile(outputFile))
            }
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.trim_save_failed), Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun formatTime(milliseconds: Int): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong())
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds.toLong()) -
                TimeUnit.MINUTES.toSeconds(minutes)
        return String.format("%d:%02d", minutes, seconds)
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

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }
}
