package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import org.threeten.bp.OffsetDateTime

@Entity(
    tableName = "measurement_table",
    primaryKeys = ["name", "timestamp"],
    foreignKeys = [ForeignKey(entity = StationEntity::class, parentColumns = ["name"], childColumns = ["name"])]
)
data class MeasurementEntity(val name: String, val timestamp: OffsetDateTime, val aqi: Double)