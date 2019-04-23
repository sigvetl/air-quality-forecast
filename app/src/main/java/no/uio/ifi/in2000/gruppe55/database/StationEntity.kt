package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "station_table")
data class StationEntity(
    @PrimaryKey
    val name: String,
    val kommune: String,
    val latitude: Double,
    val longitude: Double
)
