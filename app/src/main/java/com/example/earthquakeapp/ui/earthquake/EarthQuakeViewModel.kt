package com.example.earthquakeapp.ui.earthquake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.earthquakeapp.api.repository.EarthquakeRepository
import com.example.earthquakeapp.data.local.Earthquake
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class EarthQuakeViewModel : ViewModel(){

    private var _earthquakesLiveData = MutableLiveData<List<Earthquake>>()
    val earthquakesLiveData : LiveData<List<Earthquake>> get() = _earthquakesLiveData
    private val earthquakeRepository = EarthquakeRepository()

    init {
        Executors.newSingleThreadScheduledExecutor().schedule({fetchAllEarthquakes()}, 3, TimeUnit.SECONDS)
    }

    private fun fetchAllEarthquakes() {
        viewModelScope.launch {
            _earthquakesLiveData.value = earthquakeRepository.refreshEarthquakes()
        }
    }

}