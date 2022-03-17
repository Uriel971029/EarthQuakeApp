package com.example.earthquakeapp.data.local


data class Earthquake(val id: String,
                      val magnitude : Double,
                      val place : String, val time: Long,
                      val coordinates: Array<Double>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Earthquake

        if (id != other.id) return false
        if (magnitude != other.magnitude) return false
        if (place != other.place) return false
        if (time != other.time) return false
        if (!coordinates.contentEquals(other.coordinates)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + magnitude.hashCode()
        result = 31 * result + place.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + coordinates.contentHashCode()
        return result
    }
}
