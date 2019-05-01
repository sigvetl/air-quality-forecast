package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.*
import org.threeten.bp.OffsetDateTime

@Dao
interface MeasurementDao {
    @get:Query("SELECT * FROM measurement_table")
    val all: List<MeasurementEntity>

    @Query(
        """
            SELECT * FROM measurement_table
            WHERE
                name = :name AND
                datetime(:timestamp) > datetime(timestamp, '-30 minutes') AND
                datetime(:timestamp) < datetime(timestamp, '+30 minutes')
        """
    )
    fun recentTo(name: String, timestamp: OffsetDateTime): List<MeasurementEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(measurementEntity: MeasurementEntity)

    @Delete
    fun delete(measurementEntity: MeasurementEntity)
}