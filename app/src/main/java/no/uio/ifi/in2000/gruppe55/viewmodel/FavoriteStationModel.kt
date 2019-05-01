package no.uio.ifi.in2000.gruppe55.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

/**
 * [FavoriteStationModel] implements the "View Model" of Android Architecture Components, providing a flexible and
 * extensible way to separate all the data & corresponding logic of an interface from the actual code to maintain and
 * display the interface.
 */
class FavoriteStationModel(application: Application) : AndroidViewModel(application) {

    // TODO (julianho): Observations currently have no way to only pick-up partial updates (such as insertions),
    // possibly impacting performance. If performance becomes a concern, consider how to implement such updates.
    private val mutableFavorites: MutableLiveData<HashSet<String>> by lazy {
        MutableLiveData<HashSet<String>>()
    }

    /**
     * [favorites] is a set of area names that are currently favorited. Consider converting the station names into
     * actual reified data structures.
     */
    val favorites: LiveData<HashSet<String>>
        get() = mutableFavorites

    /**
     * [favorite] marks the area with the given name [string] as favorited.
     */
    fun favorite(string: String) {
        val hashSet = mutableFavorites.value ?: hashSetOf()
        hashSet.add(string)
        mutableFavorites.postValue(hashSet)
    }

    /**
     * [defavorite] marks the area with the given name [string] as not favorited.
     */
    fun defavorite(string: String) {
        val hashSet = mutableFavorites.value ?: hashSetOf()
        hashSet.remove(string)
        mutableFavorites.postValue(hashSet)
    }
}