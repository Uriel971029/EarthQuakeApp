package com.example.earthquakeapp.ui.earthquake

import android.app.Application
import androidx.lifecycle.*
import com.example.earthquakeapp.api.repository.EarthquakeRepository
import com.example.earthquakeapp.data.local.Earthquake
import com.example.earthquakeapp.database.getInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class EarthQuakeViewModel(application: Application) : AndroidViewModel(application) {

    private val earthquakeRepository = EarthquakeRepository(getInstance(application))

    private var _earthquakesLiveData = MutableLiveData<List<Earthquake>>()

    val earthquakesLiveData : LiveData<List<Earthquake>> get() = _earthquakesLiveData

    init {
        Executors.newSingleThreadScheduledExecutor().schedule(
            {
            getStoredEarthquakesFromDatabase()
            fetchAllEarthquakes()
            }, 3, TimeUnit.SECONDS)
    }

    private fun getStoredEarthquakesFromDatabase() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _earthquakesLiveData.postValue(earthquakeRepository.earthquakes)
            }
        }
    }

    private fun fetchAllEarthquakes() {
        viewModelScope.launch {
            earthquakeRepository.refreshEarthquakes()
        }
    }


    class EarthQuakeViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(EarthQuakeViewModel::class.java)){
                return EarthQuakeViewModel(application) as T
            }
            throw IllegalArgumentException("Cannot create EarthQuakeViewModel")
        }

    }

}