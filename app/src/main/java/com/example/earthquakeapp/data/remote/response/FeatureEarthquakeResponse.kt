package com.example.earthquakeapp.data.remote.response

data class FeatureEarthquakeResponse(
    val properties: PropertiesEarthquakeResponse,
    val geometry : GeometryEarthquakeResponse,
    val id: String)
