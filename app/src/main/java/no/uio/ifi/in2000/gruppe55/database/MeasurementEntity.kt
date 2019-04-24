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
    primaryKeys = ["eoi", "timestamp"],
    foreignKeys = [ForeignKey(entity = StationEntity::class, parentColumns = ["eoi"], childColumns = ["eoi"])]
)
data class MeasurementEntity(
    val eoi: String,
    val timestamp: OffsetDateTime,
    val aqi: Double
) {
    @get:Ignore
    val airQualityTimeDataModel: AirQualityTimeDataModel
        get() = AirQualityTimeDataModel(
            from = TypeConverters.fromOffsetDateTime(timestamp),
            to = TypeConverters.fromOffsetDateTime(timestamp),
            variables = AirQualityVariableDataModel(aqi = VarDataModel(value = aqi))
        )
}