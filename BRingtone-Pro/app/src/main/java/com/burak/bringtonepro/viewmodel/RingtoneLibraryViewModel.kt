package com.burak.bringtonepro.viewmodel

import androidx.lifecycle.*
import com.burak.bringtonepro.data.RingtoneEntity
import com.burak.bringtonepro.repository.RingtoneRepository
import kotlinx.coroutines.launch

class RingtoneLibraryViewModel(private val repository: RingtoneRepository) : ViewModel() {
    
    val allRingtones: LiveData<List<RingtoneEntity>> = repository.allRingtones
    
    private val _selectedCategory = MutableLiveData<String>("All")
    val selectedCategory: LiveData<String> = _selectedCategory
    
    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> = _searchQuery
    
    val filteredRingtones: LiveData<List<RingtoneEntity>> = MediatorLiveData<List<RingtoneEntity>>().apply {
        addSource(allRingtones) { updateFilteredList() }
        addSource(selectedCategory) { updateFilteredList() }
        addSource(searchQuery) { updateFilteredList() }
    }
    
    private fun MediatorLiveData<List<RingtoneEntity>>.updateFilteredList() {
        val category = _selectedCategory.value ?: "All"
        val query = _searchQuery.value ?: ""
        val ringtones = allRingtones.value ?: emptyList()
        
        value = ringtones.filter { ringtone ->
            val matchesCategory = category == "All" || ringtone.category == category
            val matchesQuery = query.isEmpty() || ringtone.name.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }
    
    fun setCategory(category: String) {
        _selectedCategory.value = category
    }
    
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun insertRingtone(ringtone: RingtoneEntity) = viewModelScope.launch {
        repository.insertRingtone(ringtone)
    }
    
    fun updateRingtone(ringtone: RingtoneEntity) = viewModelScope.launch {
        repository.updateRingtone(ringtone)
    }
    
    fun deleteRingtone(ringtone: RingtoneEntity) = viewModelScope.launch {
        repository.deleteRingtone(ringtone)
    }
}

class RingtoneLibraryViewModelFactory(
    private val repository: RingtoneRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RingtoneLibraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RingtoneLibraryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
