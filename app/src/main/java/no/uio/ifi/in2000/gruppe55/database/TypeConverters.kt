package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.TypeConverter
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

object TypeConverters {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @JvmStatic
    @TypeConverter
    fun fromOffsetDateTime(offsetDateTime: OffsetDateTime?): String? {
        return offsetDateTime?.format(formatter)
    }

    @JvmStatic
    @TypeConverter
    fun toOffsetDateTime(string: String?): OffsetDateTime? {
        return string?.let {
            return formatter.parse(it, OffsetDateTime::from)
        }
    }

    @JvmStatic
    @TypeConverter
    fun toEoi(favoriteEntity: FavoriteEntity?): String? {
        return favoriteEntity?.name
    }
}