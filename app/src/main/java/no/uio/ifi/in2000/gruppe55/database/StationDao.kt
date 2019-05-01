package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.*

@Dao
interface StationDao {
    @get:Query("SELECT * FROM station_table")
    val all: List<StationEntity>

    @Query("SELECT * FROM station_table where name = :name")
    fun findByName(name: String): List<StationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stationEntity: StationEntity)

    @Delete
    fun delete(stationEntity: StationEntity)
}