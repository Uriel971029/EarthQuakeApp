package com.example.earthquakeapp.database

import android.content.Context
import androidx.room.*
import com.example.earthquakeapp.data.local.Earthquake


@Dao
interface EarthquakeDao {
    @Query("select * from earthquake")
    fun getAll() : List<EarthquakeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(earthquakes: List<EarthquakeEntity>)
}


@Entity(tableName = "earthquake")
data class EarthquakeEntity(
    @PrimaryKey
    val id: String,
    val magnitude : Double,
    val place : String,
    val time: Long,
    val latitude : Double,
    val longitude : Double)


fun EarthquakeEntity.toDomainModel() : Earthquake{
    return Earthquake(
        id = this.id,
        place = this.place,
        time = this.time,
        magnitude = this.magnitude,
        coordinates = arrayOf(this.latitude, this.longitude),
    )
}

fun Earthquake.toDatabaseModel() : EarthquakeEntity{
    return EarthquakeEntity(
        id = this.id,
        place = this.place,
        time = this.time,
        magnitude = this.magnitude,
        latitude = this.coordinates[0],
        longitude = this.coordinates[1])
}


@Database(entities = [EarthquakeEntity::class], version = 2)
abstract class EarthquakesDatabase : RoomDatabase() {
    abstract val earthquakeDao : EarthquakeDao
}



lateinit var instance : EarthquakesDatabase

fun getInstance(context: Context): EarthquakesDatabase {
    synchronized(EarthquakesDatabase::class.java) {
        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                EarthquakesDatabase::class.java,
                "earthquakes"
            ).build()
        }
        return instance
    }
}
