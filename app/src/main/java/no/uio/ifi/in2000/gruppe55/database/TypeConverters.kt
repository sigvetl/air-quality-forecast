package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.TypeConverter
import org.threeten.bp.Instant

object TypeConverters {

    @JvmStatic
    @TypeConverter
    fun fromInstant(instant: Instant?): String? {
        return instant.toString()
    }

    @JvmStatic
    @TypeConverter
    fun toInstant(string: String?): Instant? {
        return string?.let {
            return Instant.parse(it)
        }
    }
}