package com.example.earthquakeapp.data.remote.response

data class GeometryEarthquakeResponse(val coordinates: Array<Double>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeometryEarthquakeResponse

        if (!coordinates.contentEquals(other.coordinates)) return false

        return true
    }

    override fun hashCode(): Int {
        return coordinates.contentHashCode()
    }
}
