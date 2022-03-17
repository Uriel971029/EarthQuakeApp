package com.example.earthquakeapp.database

import android.content.Context
import androidx.room.*
import com.example.earthquakeapp.data.local.Earthquake


@Dao
interface EarthquakeDao {
    @Query("select * from earthquake")
    fun getAll() : List<Earthquake>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(earthquakes: List<Earthquake>)
}


@Entity(tableName = "earthquake")
data class EarthquakeEntity(
    @PrimaryKey
    val id: String,
    val magnitude : Double,
    val place : String,
    val time: Long,
    val coordinates: Array<Double>)


@Database(entities = [EarthquakeEntity::class], version = 1)
abstract class EarthquakesDatabase : RoomDatabase() {
    abstract var earthquakeDao : EarthquakeDao
}



lateinit var instance : EarthquakesDatabase

fun getInstance(context: Context): EarthquakesDatabase{
    if(!::instance.isInitialized){
        instance = Room.databaseBuilder(
            context.applicationContext,
            EarthquakesDatabase::class.java,
            "earthqueakes"
        ).build()
    }
    return instance
}
