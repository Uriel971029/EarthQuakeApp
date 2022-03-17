package com.example.earthquakeapp.api.repository

import android.util.Log
import com.example.earthquakeapp.api.ApiService
import com.example.earthquakeapp.data.local.Earthquake
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EarthquakeRepository {

    private val filteredList = mutableListOf<Earthquake>()

    suspend fun refreshEarthquakes(): List<Earthquake> {
        withContext(Dispatchers.IO){
            try {
                val earthquakesResponse = ApiService.getMethods?.getAllEarthquakes()
                earthquakesResponse.let {
                    earthquakesResponse?.features?.forEach { item ->
                        filteredList.add(
                            Earthquake(
                                item.id,
                                item.properties.mag,
                                item.properties.place,
                                item.properties.time,
                                item.geometry.coordinates
                            )
                        )
                    }
                }

            } catch (ex: Exception) {
                Log.d("error_earthquakes_api", ex.message.toString())
            }
        }
        return filteredList.toList()
    }
}