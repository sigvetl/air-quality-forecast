package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface MeasurementDao {
    @get:Query("SELECT * FROM measurement_table")
    val all: List<MeasurementEntity>

    @Insert
    fun insert(measurementEntity: MeasurementEntity)

    @Delete
    fun delete(measurementEntity: MeasurementEntity)
}