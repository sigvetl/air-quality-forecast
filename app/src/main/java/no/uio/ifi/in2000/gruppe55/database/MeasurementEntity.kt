package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.Entity
import java.time.OffsetDateTime

@Entity(tableName = "measurement_table", primaryKeys = ["name", "timestamp"])
data class MeasurementEntity(val name: String, val timestamp: OffsetDateTime, val aqi: Double)