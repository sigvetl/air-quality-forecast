package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Ignore
import no.uio.ifi.in2000.gruppe55.AirQualityTimeDataModel
import no.uio.ifi.in2000.gruppe55.AirQualityVariableDataModel
import no.uio.ifi.in2000.gruppe55.VarDataModel
import org.threeten.bp.Instant

@Entity(
    tableName = "measurement_table",
    primaryKeys = ["eoi", "timestamp"],
    foreignKeys = [ForeignKey(entity = StationEntity::class, parentColumns = ["eoi"], childColumns = ["eoi"])]
)
data class MeasurementEntity(
    val eoi: String,
    val timestamp: Instant,
    val aqi: Double
) {
    @get:Ignore
    val airQualityTimeDataModel: AirQualityTimeDataModel
        get() = AirQualityTimeDataModel(
            from = timestamp.toString(),
            to = timestamp.toString(),
            variables = AirQualityVariableDataModel(aqi = VarDataModel(value = aqi))
        )
}