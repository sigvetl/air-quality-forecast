package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.*

@Dao
interface StationDao {
    @get:Query("SELECT * FROM station_table")
    val all: List<StationEntity>

    @Query("SELECT eoi = :eoi FROM station_table WHERE eoi = :eoi")
    fun has(eoi: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stationEntity: StationEntity)

    @Delete
    fun delete(stationEntity: StationEntity)
}