package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(
    tableName = "favorite_table",
    foreignKeys = [ForeignKey(entity = StationEntity::class, parentColumns = ["name"], childColumns = ["name"])]
)
data class FavoriteEntity(@PrimaryKey val name: String)