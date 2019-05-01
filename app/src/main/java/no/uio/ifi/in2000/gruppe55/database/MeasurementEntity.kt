package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Ignore
import no.uio.ifi.in2000.gruppe55.AirQualityTimeDataModel
import no.uio.ifi.in2000.gruppe55.AirQualityVariableDataModel
import no.uio.ifi.in2000.gruppe55.VarDataModel
import org.threeten.bp.OffsetDateTime

@Entity(
    tableName = "measurement_table",
    primaryKeys = ["name", "timestamp"],
    foreignKeys = [ForeignKey(entity = StationEntity::class, parentColumns = ["name"], childColumns = ["name"])]
)
data class MeasurementEntity(val name: String, val timestamp: OffsetDateTime, val aqi: Double) {
    @get:Ignore
    val airQualityTimeDataModel: AirQualityTimeDataModel
        get() = AirQualityTimeDataModel(
            from = TypeConverters.fromOffsetDateTime(timestamp),
            to = TypeConverters.fromOffsetDateTime(timestamp),
            variables = AirQualityVariableDataModel(aqi = VarDataModel(value = aqi))
        )
}