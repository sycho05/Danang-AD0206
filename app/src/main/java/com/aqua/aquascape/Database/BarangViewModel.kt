package com.aqua.aquascape.Database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BarangViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DatabaseRepository
    val allBarang: LiveData<List<Barang>>
    init {
        val dao = AquascapeDatabase.getDatabase(application,
            viewModelScope).dao()
        repository = DatabaseRepository(dao)
        allBarang = repository.allBarang
    }
    fun insert(listBarang: Barang) = viewModelScope.launch {
        repository.insert(listBarang)
    }
    fun update(listBarang: Barang) = viewModelScope.launch {
        repository.update(listBarang)
    }

    fun deleteData(listBarang: Barang) = viewModelScope.launch {
        repository.deleteData(listBarang)
    }
}