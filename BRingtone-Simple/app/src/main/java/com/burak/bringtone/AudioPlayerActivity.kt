package com.burak.bringtone

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import java.util.concurrent.TimeUnit

class AudioPlayerActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var audioTitle: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var currentTime: TextView
    private lateinit var totalTime: TextView
    private lateinit var playPauseButton: Button
    private lateinit var trimAudioButton: Button
    private lateinit var setRingtoneButton: Button

    private var mediaPlayer: MediaPlayer? = null
    private var audioUri: Uri? = null
    private val handler = Handler(Looper.getMainLooper())
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        initViews()
        setupToolbar()
        
        // Get audio URI from intent
        val uriString = intent.getStringExtra("AUDIO_URI")
        audioUri = Uri.parse(uriString)
        
        audioUri?.let {
            setupMediaPlayer(it)
            audioTitle.text = getFileName(it)
        }

        setupListeners()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        audioTitle = findViewById(R.id.audioTitle)
        seekBar = findViewById(R.id.seekBar)
        currentTime = findViewById(R.id.currentTime)
        totalTime = findViewById(R.id.totalTime)
        playPauseButton = findViewById(R.id.playPauseButton)
        trimAudioButton = findViewById(R.id.trimAudioButton)
        setRingtoneButton = findViewById(R.id.setRingtoneButton)
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupMediaPlayer(uri: Uri) {
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(applicationContext, uri)
                prepare()
                
                // Set total duration
                val duration = this.duration
                seekBar.max = duration
                totalTime.text = formatTime(duration)
                currentTime.text = formatTime(0)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error loading audio file", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun setupListeners() {
        playPauseButton.setOnClickListener {
            togglePlayPause()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                    currentTime.text = formatTime(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        trimAudioButton.setOnClickListener {
            audioUri?.let { uri ->
                val intent = Intent(this, AudioTrimmerActivity::class.java).apply {
                    putExtra("AUDIO_URI", uri.toString())
                }
                startActivity(intent)
            }
        }

        setRingtoneButton.setOnClickListener {
            audioUri?.let { uri ->
                RingtoneHelper.setRingtone(this, uri)
            }
        }
    }

    private fun togglePlayPause() {
        mediaPlayer?.let { player ->
            if (isPlaying) {
                player.pause()
                playPauseButton.text = getString(R.string.play)
                playPauseButton.setCompoundDrawablesWithIntrinsicBounds(
                    android.R.drawable.ic_media_play, 0, 0, 0
                )
                stopSeekBarUpdate()
            } else {
                player.start()
                playPauseButton.text = getString(R.string.pause)
                playPauseButton.setCompoundDrawablesWithIntrinsicBounds(
                    android.R.drawable.ic_media_pause, 0, 0, 0
                )
                updateSeekBar()
            }
            isPlaying = !isPlaying
        }
    }

    private fun updateSeekBar() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    if (it.isPlaying) {
                        val current = it.currentPosition
                        seekBar.progress = current
                        currentTime.text = formatTime(current)
                        handler.postDelayed(this, 100)
                    }
                }
            }
        }, 100)
    }

    private fun stopSeekBarUpdate() {
        handler.removeCallbacksAndMessages(null)
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
        stopSeekBarUpdate()
    }

    override fun onPause() {
        super.onPause()
        if (isPlaying) {
            togglePlayPause()
        }
    }
}
