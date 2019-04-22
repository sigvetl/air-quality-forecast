package no.uio.ifi.in2000.gruppe55.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

@Database(
    entities = [MeasurementEntity::class, StationEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(no.uio.ifi.in2000.gruppe55.database.TypeConverters::class)
abstract class DailyForecastDatabase: RoomDatabase() {

    abstract fun measurementDao(): MeasurementDao

    abstract fun stationDao(): StationDao

    companion object {

        private var instance: DailyForecastDatabase? = null

        @Synchronized
        fun of(context: Context): DailyForecastDatabase {
            instance = instance ?: Room
                .databaseBuilder(context, DailyForecastDatabase::class.java, "daily_forecast_database")
                .fallbackToDestructiveMigration()
                .build()
            return instance!!
        }
    }
}