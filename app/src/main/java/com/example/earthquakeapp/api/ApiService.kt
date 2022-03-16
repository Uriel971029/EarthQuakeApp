package com.example.earthquakeapp.api

import com.example.earthquakeapp.data.remote.response.NetworkEarthquakesResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

object ApiService {

    var retrofit : Retrofit
    val baseUrl = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/"
    var getMethods: eartquakeInterface?

    init {
        retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(MoshiConverterFactory.create()).build()
        getMethods = retrofit.create(eartquakeInterface::class.java)
    }

    interface eartquakeInterface {
        @GET("summary/all_hour.geojson")
        suspend fun getAllEarthquakes() : NetworkEarthquakesResponse
    }

}