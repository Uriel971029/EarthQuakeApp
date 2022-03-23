package com.example.earthquakeapp.api.repository

import android.util.Log
import com.example.earthquakeapp.api.ApiService
import com.example.earthquakeapp.data.local.Earthquake
import com.example.earthquakeapp.database.EarthquakesDatabase
import com.example.earthquakeapp.database.toDatabaseModel
import com.example.earthquakeapp.database.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EarthquakeRepository(private val database: EarthquakesDatabase) {

    val earthquakes : MutableList<Earthquake> by lazy { database.earthquakeDao.getAll().map { it.toDomainModel() }.toMutableList() }

    suspend fun refreshEarthquakes() {
        withContext(Dispatchers.IO){
            try {
                val earthquakesResponse = ApiService.getMethods?.getAllEarthquakes()
                earthquakesResponse.let {
                    earthquakesResponse?.features?.forEach { item ->
                        earthquakes.add(
                            Earthquake(
                                item.id,
                                item.properties.mag,
                                item.properties.place,
                                item.properties.time,
                                item.geometry.coordinates
                            )
                        )
                    }
                    database.earthquakeDao.insertAll(earthquakes.toList().map { it.toDatabaseModel() })
                }
            } catch (ex: Exception) {
                Log.d("error_earthquakes_api", ex.message.toString())
            }
        }
    }
}