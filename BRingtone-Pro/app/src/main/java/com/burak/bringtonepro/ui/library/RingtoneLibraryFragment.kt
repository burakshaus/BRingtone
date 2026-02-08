package com.burak.bringtonepro.ui.library

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.burak.bringtonepro.BRingtoneProApplication
import com.burak.bringtonepro.R
import com.burak.bringtonepro.data.RingtoneEntity
import com.burak.bringtonepro.databinding.FragmentRingtoneLibraryBinding
import com.burak.bringtonepro.viewmodel.RingtoneLibraryViewModel
import com.burak.bringtonepro.viewmodel.RingtoneLibraryViewModelFactory

class RingtoneLibraryFragment : Fragment() {

    private var _binding: FragmentRingtoneLibraryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RingtoneLibraryViewModel by viewModels {
        RingtoneLibraryViewModelFactory(
            (requireActivity().application as BRingtoneProApplication).repository
        )
    }

    private val audioPickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { addRingtoneFromUri(it) }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openAudioPicker()
        } else {
            Toast.makeText(context, R.string.permission_required, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRingtoneLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.fabAddRingtone.setOnClickListener {
            checkPermissionAndPickAudio()
        }

        binding.chipAll.setOnClickListener {
            viewModel.setCategory("All")
        }

        binding.chipCustom.setOnClickListener {
            viewModel.setCategory("Custom")
        }

        binding.chipSystem.setOnClickListener {
            viewModel.setCategory("System")
        }
    }

    private fun observeViewModel() {
        viewModel.filteredRingtones.observe(viewLifecycleOwner) { ringtones ->
            if (ringtones.isEmpty()) {
                binding.emptyStateLayout.visibility = View.VISIBLE
                binding.ringtonesRecyclerView.visibility = View.GONE
            } else {
                binding.emptyStateLayout.visibility = View.GONE
                binding.ringtonesRecyclerView.visibility = View.VISIBLE
                // Setup adapter here when implemented
            }
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
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                openAudioPicker()
            }
            else -> {
                permissionLauncher.launch(permission)
            }
        }
    }

    private fun openAudioPicker() {
        audioPickerLauncher.launch("audio/*")
    }

    private fun addRingtoneFromUri(uri: Uri) {
        val ringtone = RingtoneEntity(
            name = getFileName(uri),
            uri = uri.toString(),
            duration = 0,
            dateAdded = System.currentTimeMillis()
        )
        viewModel.insertRingtone(ringtone)
        Toast.makeText(context, R.string.ringtone_added, Toast.LENGTH_SHORT).show()
    }

    private fun getFileName(uri: Uri): String {
        var fileName = "Unknown"
        requireContext().contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = cursor.getString(nameIndex)
                }
            }
        }
        return fileName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
