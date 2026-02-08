package com.burak.bringtonepro.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.burak.bringtonepro.databinding.FragmentAudioEditorBinding
import com.burak.bringtonepro.viewmodel.AudioEditorViewModel

class AudioEditorFragment : Fragment() {

    private var _binding: FragmentAudioEditorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AudioEditorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAudioEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.fadeInSwitch.setOnCheckedChangeListener { _, _ ->
            viewModel.toggleFadeIn()
        }

        binding.fadeOutSwitch.setOnCheckedChangeListener { _, _ ->
            viewModel.toggleFadeOut()
        }

        binding.normalizeSwitch.setOnCheckedChangeListener { _, _ ->
            viewModel.toggleNormalizeVolume()
        }

        binding.previewButton.setOnClickListener {
            // Preview functionality
        }

        binding.saveButton.setOnClickListener {
            // Save functionality
        }
    }

    private fun observeViewModel() {
        // Observe ViewModel LiveData
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
