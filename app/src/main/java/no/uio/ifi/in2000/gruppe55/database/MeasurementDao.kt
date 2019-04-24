package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import org.threeten.bp.OffsetDateTime

@Dao
interface MeasurementDao {
    @get:Query("SELECT * FROM measurement_table")
    val all: List<MeasurementEntity>

    @Query(
        """
            SELECT
                name = :name AND
                datetime(timestamp) = datetime(:timestamp)
            FROM measurement_table
            WHERE
                name = :name AND
                datetime(timestamp) = datetime(:timestamp)
        """
    )
    fun has(name: String, timestamp: OffsetDateTime): Boolean

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

    @Insert
    fun insert(measurementEntity: MeasurementEntity)

    @Delete
    fun delete(measurementEntity: MeasurementEntity)
}