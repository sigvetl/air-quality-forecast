package no.uio.ifi.in2000.gruppe55

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object Airqualityforecast {
    private const val url = "https://in2000-apiproxy.ifi.uio.no/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(AirqualityforecastService::class.java)

    suspend fun main(
        areaclass: String? = null,
        lat: Double? = null,
        lon: Double? = null,
        reftime: String? = null,
        show: String? = null,
        station: String? = null
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

    suspend fun aqiDescription(): AQIDescriptionModel {
        return service.aqiDescription().await()
    }

    suspend fun areaclasses(): List<AreaClassModel> {
        return service.areaclasses().await()
    }

    suspend fun areas(areaclass: String? = null): List<LocationModel> {
        return service.areas(areaclass).await()
    }

    suspend fun healthz(): ResponseBody {
        return service.healthz().await()
    }

    suspend fun met(
        reftime: String? = null,
        station: String
    ): AirQualityLocationModel {
        return service.met(
            reftime,
            station
        ).await()
    }

    suspend fun metDescription(): MeteoDescriptionModel {
        return service.metDescription().await()
    }

    suspend fun reftimes(): List<RefTimeModel> {
        return service.reftimes().await()
    }

    suspend fun stations(): List<StationModel> {
        return service.stations().await()
    }

    suspend fun values(): ResponseBody {
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

data class AQIDescriptionModel(val variables: AQIVariableIntervalModel?)

data class AQIFractionModel(
    @SerializedName("nameNO")
    val nameno: String?,
    val source: String?,
    val units: String?
)

data class AQIIntervalModel(
    @SerializedName("class")
    val class_: Int?,
    val color: String?,
    val from: Double?,
    @SerializedName("short_description_NO")
    val shortDescriptionNo: String?,
    val text: String?,
    @SerializedName("text_NO")
    val textNo: String?,
    val to: Double?
)

data class AQIResultModel(
    @SerializedName("no2_concentration")
    val no2Concentration: List<AQIValueModel>?,
    @SerializedName("o3_concentration")
    val o3Concentration: List<AQIValueModel>?,
    @SerializedName("pm10_concentration")
    val pm10Concentration: List<AQIValueModel>?,
    @SerializedName("pm25_concentration")
    val pm25Concentration: List<AQIValueModel>?,
    @SerializedName("so2_concentration")
    val so2Concentration: List<AQIValueModel>?
)

data class AQIValueModel(
    @SerializedName("AQI")
    val aqi: Double?,
    val value: Double?
)

data class AQIVariableIntervalModel(
    @SerializedName("AQI")
    val aqi: AQIVariableModel?,
    @SerializedName("no2_concentration")
    val no2Concentration: AQIVariableModel?,
    @SerializedName("o3_concentration")
    val o3Concentration: AQIVariableModel?,
    @SerializedName("pm10_concentration")
    val pm10Concentration: AQIVariableModel?,
    @SerializedName("pm25_concentration")
    val pm25Concentration: AQIVariableModel?,
    @SerializedName("so2_concentration")
    val so2Concentration: AQIVariableModel?
)

data class AQIVariableModel(
    @SerializedName("aqi_formula")
    val aqiFormula: String?,
    val aqis: List<AQIIntervalModel>?,
    @SerializedName("aqis_daily_mean")
    val aqisDailyMean: List<AQIIntervalModel>?,
    @SerializedName("legend_range")
    val legendRange: List<Double>?,
    @SerializedName("nameNO")
    val nameno: String?,
    val sources: List<AQIFractionModel>?,
    val units: String?
)

data class AirQualityDataModel(val time: List<AirQualityTimeDataModel>?)

data class AirQualityLocationModel(
    val data: AirQualityDataModel?,
    val meta: MetaModel?
)

data class AirQualitySourceModel(
    val sources: List<String>?,
    val variables: List<String>?
)

data class AirQualityTimeDataModel(
    val from: String?,
    val reason: AirQualitySourceModel?,
    val to: String?,
    val variables: AirQualityVariableDataModel?
)

data class AirQualityVariableDataModel(
    @SerializedName("AQI")
    val aqi: VarDataModel?,
    @SerializedName("AQI_no2")
    val aqiNo2: VarDataModel?,
    @SerializedName("AQI_o3")
    val aqiO3: VarDataModel?,
    @SerializedName("AQI_pm10")
    val aqiPm10: VarDataModel?,
    @SerializedName("AQI_pm25")
    val aqiPm25: VarDataModel?,
    @SerializedName("no2_concentration")
    val no2Concentration: VarDataModel?,
    @SerializedName("no2_local_fraction_heating")
    val no2LocalFractionHeating: VarDataModel?,
    @SerializedName("no2_local_fraction_industry")
    val no2LocalFractionIndustry: VarDataModel?,
    @SerializedName("no2_local_fraction_shipping")
    val no2LocalFractionShipping: VarDataModel?,
    @SerializedName("no2_local_fraction_traffic_exhaust")
    val no2LocalFractionTrafficExhaust: VarDataModel?,
    @SerializedName("no2_local_fraction_traffic_nonexhaust")
    val no2LocalFractionTrafficNonexhaust: VarDataModel?,
    @SerializedName("no2_nonlocal_fraction")
    val no2NonlocalFraction: VarDataModel?,
    @SerializedName("o3_concentration")
    val o3Concentration: VarDataModel?,
    @SerializedName("o3_local_fraction_heating")
    val o3LocalFractionHeating: VarDataModel?,
    @SerializedName("o3_local_fraction_industry")
    val o3LocalFractionIndustry: VarDataModel?,
    @SerializedName("o3_local_fraction_shipping")
    val o3LocalFractionShipping: VarDataModel?,
    @SerializedName("o3_local_fraction_traffic_exhaust")
    val o3LocalFractionTrafficExhaust: VarDataModel?,
    @SerializedName("o3_local_fraction_traffic_nonexhaust")
    val o3LocalFractionTrafficNonexhaust: VarDataModel?,
    @SerializedName("o3_nonlocal_fraction")
    val o3NonlocalFraction: VarDataModel?,
    @SerializedName("pm10_concentration")
    val pm10Concentration: VarDataModel?,
    @SerializedName("pm10_local_fraction_heating")
    val pm10LocalFractionHeating: VarDataModel?,
    @SerializedName("pm10_local_fraction_industry")
    val pm10LocalFractionIndustry: VarDataModel?,
    @SerializedName("pm10_local_fraction_shipping")
    val pm10LocalFractionShipping: VarDataModel?,
    @SerializedName("pm10_local_fraction_traffic_exhaust")
    val pm10LocalFractionTrafficExhaust: VarDataModel?,
    @SerializedName("pm10_local_fraction_traffic_nonexhaust")
    val pm10LocalFractionTrafficNonexhaust: VarDataModel?,
    @SerializedName("pm10_nonlocal_fraction")
    val pm10NonlocalFraction: VarDataModel?,
    @SerializedName("pm25_concentration")
    val pm25Concentration: VarDataModel?,
    @SerializedName("pm25_local_fraction_heating")
    val pm25LocalFractionHeating: VarDataModel?,
    @SerializedName("pm25_local_fraction_industry")
    val pm25LocalFractionIndustry: VarDataModel?,
    @SerializedName("pm25_local_fraction_shipping")
    val pm25LocalFractionShipping: VarDataModel?,
    @SerializedName("pm25_local_fraction_traffic_exhaust")
    val pm25LocalFractionTrafficExhaust: VarDataModel?,
    @SerializedName("pm25_local_fraction_traffic_nonexhaust")
    val pm25LocalFractionTrafficNonexhaust: VarDataModel?,
    @SerializedName("pm25_nonlocal_fraction")
    val pm25NonlocalFraction: VarDataModel?
)

data class AreaClassModel(
    val desc: String?,
    val name: String?
)

data class LocationModel(
    val areaclass: String?,
    val areacode: String?,
    val latitude: Double?,
    val longitude: Double?,
    val name: String?,
    val superareacode: String?
)

data class MetaModel(
    val location: LocationModel?,
    val reftime: String?,
    val sublocations: List<LocationModel>?,
    val superlocation: LocationModel?
)

data class MeteoDescriptionModel(val variables: MeteoVariablesModel?)

data class MeteoVariableModel(
    @SerializedName("nameNO")
    val nameno: String?,
    @SerializedName("range_base")
    val rangeBase: Double?,
    @SerializedName("range_max")
    val rangeMax: Double?,
    @SerializedName("range_min")
    val rangeMin: Double?,
    val units: String?
)

data class MeteoVariablesModel(
    @SerializedName("air_temperature_0m")
    val airTemperature0m: MeteoVariableModel?,
    @SerializedName("air_temperature_100m")
    val airTemperature100m: MeteoVariableModel?,
    @SerializedName("air_temperature_200m")
    val airTemperature200m: MeteoVariableModel?,
    @SerializedName("air_temperature_20m")
    val airTemperature20m: MeteoVariableModel?,
    @SerializedName("air_temperature_2m")
    val airTemperature2m: MeteoVariableModel?,
    @SerializedName("air_temperature_500m")
    val airTemperature500m: MeteoVariableModel?,
    @SerializedName("atmosphere_boundary_layer_thickness")
    val atmosphereBoundaryLayerThickness: MeteoVariableModel?,
    @SerializedName("cloud_area_fraction")
    val cloudAreaFraction: MeteoVariableModel?,
    @SerializedName("integral_of_surface_net_downward_radiation_flux_wrt_time")
    val integralOfSurfaceNetDownwardRadiationFluxWrtTime: MeteoVariableModel?,
    @SerializedName("rainfall_amount")
    val rainfallAmount: MeteoVariableModel?,
    @SerializedName("relative_humidity_2m")
    val relativeHumidity2m: MeteoVariableModel?,
    @SerializedName("snowfall_amount")
    val snowfallAmount: MeteoVariableModel?,
    @SerializedName("surface_air_pressure")
    val surfaceAirPressure: MeteoVariableModel?,
    @SerializedName("wind_direction")
    val windDirection: MeteoVariableModel?,
    @SerializedName("wind_speed")
    val windSpeed: MeteoVariableModel?
)

data class RefTimeModel(val reftimes: List<String>?)

data class SimpleLocationModel(
    val areacode: String?,
    val name: String?
)

data class StationModel(
    val delomrade: SimpleLocationModel?,
    val eoi: String?,
    val grunnkrets: SimpleLocationModel?,
    val height: Double?,
    val kommune: SimpleLocationModel?,
    val latitude: Double?,
    val longitude: Double?,
    val name: String?
)

data class VarDataModel(
    val units: String?,
    val value: Double?
)
