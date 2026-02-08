package com.burak.bringtonepro.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AudioEditorViewModel : ViewModel() {
    
    private val _audioUri = MutableLiveData<Uri?>()
    val audioUri: LiveData<Uri?> = _audioUri
    
    private val _audioDuration = MutableLiveData<Long>(0)
    val audioDuration: LiveData<Long> = _audioDuration
    
    private val _trimStart = MutableLiveData<Long>(0)
    val trimStart: LiveData<Long> = _trimStart
    
    private val _trimEnd = MutableLiveData<Long>(0)
    val trimEnd: LiveData<Long> = _trimEnd
    
    private val _isPlaying = MutableLiveData<Boolean>(false)
    val isPlaying: LiveData<Boolean> = _isPlaying
    
    private val _currentPosition = MutableLiveData<Long>(0)
    val currentPosition: LiveData<Long> = _currentPosition
    
    // Audio effects
    private val _fadeInEnabled = MutableLiveData<Boolean>(false)
    val fadeInEnabled: LiveData<Boolean> = _fadeInEnabled
    
    private val _fadeOutEnabled = MutableLiveData<Boolean>(false)
    val fadeOutEnabled: LiveData<Boolean> = _fadeOutEnabled
    
    private val _normalizeVolume = MutableLiveData<Boolean>(false)
    val normalizeVolume: LiveData<Boolean> = _normalizeVolume
    
    private val _pitchAdjustment = MutableLiveData<Float>(1.0f) // 0.5 to 2.0
    val pitchAdjustment: LiveData<Float> = _pitchAdjustment
    
    fun setAudioUri(uri: Uri, duration: Long) {
        _audioUri.value = uri
        _audioDuration.value = duration
        _trimStart.value = 0
        _trimEnd.value = duration
    }
    
    fun setTrimStart(position: Long) {
        _trimStart.value = position.coerceIn(0, _trimEnd.value ?: 0)
    }
    
    fun setTrimEnd(position: Long) {
        _trimEnd.value = position.coerceIn(_trimStart.value ?: 0, _audioDuration.value ?: 0)
    }
    
    fun setPlayingState(playing: Boolean) {
        _isPlaying.value = playing
    }
    
    fun setCurrentPosition(position: Long) {
        _currentPosition.value = position
    }
    
    fun toggleFadeIn() {
        _fadeInEnabled.value = !(_fadeInEnabled.value ?: false)
    }
    
    fun toggleFadeOut() {
        _fadeOutEnabled.value = !(_fadeOutEnabled.value ?: false)
    }
    
    fun toggleNormalizeVolume() {
        _normalizeVolume.value = !(_normalizeVolume.value ?: false)
    }
    
    fun setPitchAdjustment(pitch: Float) {
        _pitchAdjustment.value = pitch.coerceIn(0.5f, 2.0f)
    }
    
    fun getTrimmedDuration(): Long {
        val start = _trimStart.value ?: 0
        val end = _trimEnd.value ?: 0
        return end - start
    }
}
