package no.uio.ifi.in2000.gruppe55.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import no.uio.ifi.in2000.gruppe55.database.DailyForecastDatabase
import no.uio.ifi.in2000.gruppe55.database.FavoriteDao
import no.uio.ifi.in2000.gruppe55.database.FavoriteEntity

/**
 * [FavoriteStationModel] implements the "View Model" of Android Architecture Components, providing a flexible and
 * extensible way to separate all the data & corresponding logic of an interface from the actual code to maintain and
 * display the interface.
 */
class FavoriteStationModel(application: Application) : AndroidViewModel(application) {

    // List of databases relevant to this view model.

    private val dailyForecastDatabase: DailyForecastDatabase by lazy {
        DailyForecastDatabase.of(application)
    }

    private val favoriteDao: FavoriteDao by lazy {
        dailyForecastDatabase.favoriteDao()
    }

    /**
     * [favorites] is a set of area names that are currently favorited. Consider converting the station names into
     * actual reified data structures.
     */
    val favorites: LiveData<HashSet<String>> by lazy {
        Transformations.map(favoriteDao.all) {
            stationList -> HashSet(stationList.map { stationModel -> stationModel.name })
        }
    }

    /**
     * [favorite] marks the area with the given name [string] as favorited.
     */
    fun favorite(string: String) {
        favoriteDao.add(FavoriteEntity(string))
    }

    /**
     * [defavorite] marks the area with the given name [string] as not favorited.
     */
    fun defavorite(string: String) {
        favoriteDao.remove(FavoriteEntity(string))
    }
}