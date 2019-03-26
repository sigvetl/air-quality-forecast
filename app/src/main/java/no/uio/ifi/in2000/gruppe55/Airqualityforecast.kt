package no.uio.ifi.in2000.gruppe55

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object Airqualityforecast : AirqualityforecastInterface {
    private const val url = "https://in2000-apiproxy.ifi.uio.no/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(AirqualityforecastService::class.java)

    override suspend fun main(
        areaclass: String?,
        lat: Double?,
        lon: Double?,
        reftime: String?,
        show: String?,
        station: String?
    ): AirQualityLocationModel {
        return service.main(
            areaclass,
            lat,
            lon,
            reftime,
            show,
            station
        ).await()
    }

    override suspend fun aqiDescription(): AQIDescriptionModel {
        return service.aqiDescription().await()
    }

    override suspend fun areaclasses(): List<AreaClassModel> {
        return service.areaclasses().await()
    }

    override suspend fun areas(areaclass: String?): List<LocationModel> {
        return service.areas(areaclass).await()
    }

    override suspend fun healthz(): ResponseBody {
        return service.healthz().await()
    }

    override suspend fun met(
        reftime: String?,
        station: String
    ): AirQualityLocationModel {
        return service.met(
            reftime,
            station
        ).await()
    }

    override suspend fun metDescription(): MeteoDescriptionModel {
        return service.metDescription().await()
    }

    override suspend fun reftimes(): List<RefTimeModel> {
        return service.reftimes().await()
    }

    override suspend fun stations(): List<StationModel> {
        return service.stations().await()
    }

    override suspend fun values(): ResponseBody {
        return service.values().await()
    }
}

interface AirqualityforecastService {
    @GET("weatherapi/airqualityforecast/0.1/")
    fun main(
        @Query("areaclass")
        areaclass: String? = null,
        @Query("lat")
        lat: Double? = null,
        @Query("lon")
        lon: Double? = null,
        @Query("reftime")
        reftime: String? = null,
        @Query("show")
        show: String? = null,
        @Query("station")
        station: String? = null
    ): Deferred<AirQualityLocationModel>

    @GET("weatherapi/airqualityforecast/0.1/aqi_description")
    fun aqiDescription(): Deferred<AQIDescriptionModel>

    @GET("weatherapi/airqualityforecast/0.1/areaclasses")
    fun areaclasses(): Deferred<List<AreaClassModel>>

    @GET("weatherapi/airqualityforecast/0.1/areas")
    fun areas(
        @Query("areaclass")
        areaclass: String? = null
    ): Deferred<List<LocationModel>>

    @GET("weatherapi/airqualityforecast/0.1/healthz")
    fun healthz(): Deferred<ResponseBody>

    @GET("weatherapi/airqualityforecast/0.1/met")
    fun met(
        @Query("reftime")
        reftime: String? = null,
        @Query("station")
        station: String
    ): Deferred<AirQualityLocationModel>

    @GET("weatherapi/airqualityforecast/0.1/met_description")
    fun metDescription(): Deferred<MeteoDescriptionModel>

    @GET("weatherapi/airqualityforecast/0.1/reftimes")
    fun reftimes(): Deferred<List<RefTimeModel>>

    @GET("weatherapi/airqualityforecast/0.1/stations")
    fun stations(): Deferred<List<StationModel>>

    @GET("weatherapi/airqualityforecast/0.1/values")
    fun values(): Deferred<ResponseBody>
}

