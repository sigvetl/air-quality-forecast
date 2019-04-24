package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import no.uio.ifi.in2000.gruppe55.SimpleLocationModel
import no.uio.ifi.in2000.gruppe55.StationModel

@Entity(tableName = "station_table")
data class StationEntity(
    @PrimaryKey
    val eoi: String,
    val name: String,
    val kommune: String,
    val latitude: Double,
    val longitude: Double
) {
    @get:Ignore
    val stationModel: StationModel
        get() = StationModel(
            eoi = eoi,
            name = name,
            kommune = SimpleLocationModel(name = kommune),
            latitude = latitude,
            longitude = longitude
        )
}
