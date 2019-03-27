package no.uio.ifi.in2000.gruppe55

import io.reactivex.Observable
import retrofit2.http.GET

interface WeatherInterface {
    @GET("airqualityforecast/0.1/stations")
    fun getAllStations(): Observable<List<Stations>>
}