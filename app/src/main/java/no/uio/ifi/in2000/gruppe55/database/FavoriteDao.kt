package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_table WHERE name = :favoriteEntity")
    fun contains(favoriteEntity: FavoriteEntity): Boolean

    @Insert
    fun add(favoriteEntity: FavoriteEntity)

    @Delete
    fun remove(favoriteEntity: FavoriteEntity)
}