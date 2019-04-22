package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface StationDao {
    @get:Query("SELECT * FROM station_table")
    val all: List<StationEntity>

    @Query("SELECT * FROM station_table where name = :name")
    fun findByName(name: String): List<StationEntity>

    @Insert
    fun insert(stationEntity: StationEntity)

    @Delete
    fun delete(stationEntity: StationEntity)
}