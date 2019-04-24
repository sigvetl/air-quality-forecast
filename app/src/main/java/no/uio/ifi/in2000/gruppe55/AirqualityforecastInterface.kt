package no.uio.ifi.in2000.gruppe55

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

/**
 * [AirqualityforecastInterface] provides an abstract interface that represents
 * [Airqualityforecast](https://in2000-apiproxy.ifi.uio.no/weatherapi/airqualityforecast/0.1/documentation).
 *
 * Each of the routes from the API definition of Airqualityforecast is replicated in a relatively similar form; there is
 * no magic or other tricks. To get more thorough documentation, consider checking the API of Airqualityforecast.
 *
 * To keep methods general, consider accepting any `(airqualityforecast : [AirqualityforecastInterface]` in order to
 * admit easy testing and extensibility.
 */
interface AirqualityforecastInterface {
    /**
     * [main] represents the route
     * [/](https://in2000-apiproxy.ifi.uio.no/weatherapi/airqualityforecast/0.1/documentation#%2F). Since a blank
     * identifier is not a valid name--and said route is essentially the most important and useful one--the name of the
     * route becomes "main".
     *
     * Note that [main] heavily uses Kotlin's
     * [named and default argument syntax](https://kotlinlang.org/docs/reference/functions.html#function-usage) to
     * provide a simple and convenient interface. In other words, rather than writing
     *
     * ```kotlin
     * val values = Airqualityforecast.main(null, null, null, null, null, station)
     * ```
     *
     * write
     *
     * ```kotlin
     * val values = Airqualityforecast.main(station = station)
     * ```
     *
     * keeping the code simple and compositional.
     */
    suspend fun main(
        areaclass: String? = null,
        lat: Double? = null,
        lon: Double? = null,
        reftime: String? = null,
        show: String? = null,
        station: String? = null
    ): AirQualityLocationModel

    /**
     * [aqiDescription] represents the route
     * [/aqi_description](https://in2000-apiproxy.ifi.uio.no/weatherapi/airqualityforecast/0.1/documentation#%2Faqi_description).
     */
    suspend fun aqiDescription(): AQIDescriptionModel

    /**
     * [areaclasses] represents a route which is not listed in the official documentation, but should still be valid.
     */
    suspend fun areaclasses(): List<AreaClassModel>

    /**
     * [areas] represents the route
     * [/areas](https://in2000-apiproxy.ifi.uio.no/weatherapi/airqualityforecast/0.1/documentation#%2Fareas).
     */
    suspend fun areas(areaclass: String? = null): List<LocationModel>

    /**
     * [healthz] represents a route which is not listed in the official documentation, but should still be valid.
     * Specifially, [healthz] should yield whether Airqualityforecast is currently usable remotely.
     */
    suspend fun healthz(): ResponseBody

    /**
     * [met] represents the route
     * [/met](https://in2000-apiproxy.ifi.uio.no/weatherapi/airqualityforecast/0.1/documentation#%2Fmet).
     */
    suspend fun met(
        reftime: String? = null,
        station: String
    ): AirQualityLocationModel

    /**
     * [metDescription] represents the route
     * [/met_description](https://in2000-apiproxy.ifi.uio.no/weatherapi/airqualityforecast/0.1/documentation#%2Fmet_description).
     */
    suspend fun metDescription(): MeteoDescriptionModel

    /**
     * [reftimes] represents the route
     * [/reftimes](https://in2000-apiproxy.ifi.uio.no/weatherapi/airqualityforecast/0.1/documentation#%2Freftimes).
     */
    suspend fun reftimes(): List<RefTimeModel>

    /**
     * [stations] represents the route
     * [/stations](https://in2000-apiproxy.ifi.uio.no/weatherapi/airqualityforecast/0.1/documentation#%2Fstations).
     */
    suspend fun stations(): List<StationModel>

    /**
     * [values] represents a route which is not listed in the official documentation, but should still be valid.
     */
    suspend fun values(): ResponseBody
}

data class AQIDescriptionModel(val variables: AQIVariableIntervalModel? = null)

data class AQIFractionModel(
    @SerializedName("nameNO")
    val nameno: String? = null,
    val source: String? = null,
    val units: String? = null
)

data class AQIIntervalModel(
    @SerializedName("class")
    val class_: Int? = null,
    val color: String? = null,
    val from: Double? = null,
    @SerializedName("short_description_NO")
    val shortDescriptionNo: String? = null,
    val text: String? = null,
    @SerializedName("text_NO")
    val textNo: String? = null,
    val to: Double? = null
)

data class AQIResultModel(
    @SerializedName("no2_concentration")
    val no2Concentration: List<AQIValueModel>? = null,
    @SerializedName("o3_concentration")
    val o3Concentration: List<AQIValueModel>? = null,
    @SerializedName("pm10_concentration")
    val pm10Concentration: List<AQIValueModel>? = null,
    @SerializedName("pm25_concentration")
    val pm25Concentration: List<AQIValueModel>? = null,
    @SerializedName("so2_concentration")
    val so2Concentration: List<AQIValueModel>? = null
)

data class AQIValueModel(
    @SerializedName("AQI")
    val aqi: Double? = null,
    val value: Double? = null
)

data class AQIVariableIntervalModel(
    @SerializedName("AQI")
    val aqi: AQIVariableModel? = null,
    @SerializedName("no2_concentration")
    val no2Concentration: AQIVariableModel? = null,
    @SerializedName("o3_concentration")
    val o3Concentration: AQIVariableModel? = null,
    @SerializedName("pm10_concentration")
    val pm10Concentration: AQIVariableModel? = null,
    @SerializedName("pm25_concentration")
    val pm25Concentration: AQIVariableModel? = null,
    @SerializedName("so2_concentration")
    val so2Concentration: AQIVariableModel? = null
)

data class AQIVariableModel(
    @SerializedName("aqi_formula")
    val aqiFormula: String? = null,
    val aqis: List<AQIIntervalModel>? = null,
    @SerializedName("aqis_daily_mean")
    val aqisDailyMean: List<AQIIntervalModel>? = null,
    @SerializedName("legend_range")
    val legendRange: List<Double>? = null,
    @SerializedName("nameNO")
    val nameno: String? = null,
    val sources: List<AQIFractionModel>? = null,
    val units: String? = null
)

data class AirQualityDataModel(val time: List<AirQualityTimeDataModel>? = null)

data class AirQualityLocationModel(
    val data: AirQualityDataModel? = null,
    val meta: MetaModel? = null
)

data class AirQualitySourceModel(
    val sources: List<String>? = null,
    val variables: List<String>? = null
)

data class AirQualityTimeDataModel(
    val from: String? = null,
    val reason: AirQualitySourceModel? = null,
    val to: String? = null,
    val variables: AirQualityVariableDataModel? = null
)

data class AirQualityVariableDataModel(
    @SerializedName("AQI")
    val aqi: VarDataModel? = null,
    @SerializedName("AQI_no2")
    val aqiNo2: VarDataModel? = null,
    @SerializedName("AQI_o3")
    val aqiO3: VarDataModel? = null,
    @SerializedName("AQI_pm10")
    val aqiPm10: VarDataModel? = null,
    @SerializedName("AQI_pm25")
    val aqiPm25: VarDataModel? = null,
    @SerializedName("no2_concentration")
    val no2Concentration: VarDataModel? = null,
    @SerializedName("no2_local_fraction_heating")
    val no2LocalFractionHeating: VarDataModel? = null,
    @SerializedName("no2_local_fraction_industry")
    val no2LocalFractionIndustry: VarDataModel? = null,
    @SerializedName("no2_local_fraction_shipping")
    val no2LocalFractionShipping: VarDataModel? = null,
    @SerializedName("no2_local_fraction_traffic_exhaust")
    val no2LocalFractionTrafficExhaust: VarDataModel? = null,
    @SerializedName("no2_local_fraction_traffic_nonexhaust")
    val no2LocalFractionTrafficNonexhaust: VarDataModel? = null,
    @SerializedName("no2_nonlocal_fraction")
    val no2NonlocalFraction: VarDataModel? = null,
    @SerializedName("o3_concentration")
    val o3Concentration: VarDataModel? = null,
    @SerializedName("o3_local_fraction_heating")
    val o3LocalFractionHeating: VarDataModel? = null,
    @SerializedName("o3_local_fraction_industry")
    val o3LocalFractionIndustry: VarDataModel? = null,
    @SerializedName("o3_local_fraction_shipping")
    val o3LocalFractionShipping: VarDataModel? = null,
    @SerializedName("o3_local_fraction_traffic_exhaust")
    val o3LocalFractionTrafficExhaust: VarDataModel? = null,
    @SerializedName("o3_local_fraction_traffic_nonexhaust")
    val o3LocalFractionTrafficNonexhaust: VarDataModel? = null,
    @SerializedName("o3_nonlocal_fraction")
    val o3NonlocalFraction: VarDataModel? = null,
    @SerializedName("pm10_concentration")
    val pm10Concentration: VarDataModel? = null,
    @SerializedName("pm10_local_fraction_heating")
    val pm10LocalFractionHeating: VarDataModel? = null,
    @SerializedName("pm10_local_fraction_industry")
    val pm10LocalFractionIndustry: VarDataModel? = null,
    @SerializedName("pm10_local_fraction_shipping")
    val pm10LocalFractionShipping: VarDataModel? = null,
    @SerializedName("pm10_local_fraction_traffic_exhaust")
    val pm10LocalFractionTrafficExhaust: VarDataModel? = null,
    @SerializedName("pm10_local_fraction_traffic_nonexhaust")
    val pm10LocalFractionTrafficNonexhaust: VarDataModel? = null,
    @SerializedName("pm10_nonlocal_fraction")
    val pm10NonlocalFraction: VarDataModel? = null,
    @SerializedName("pm25_concentration")
    val pm25Concentration: VarDataModel? = null,
    @SerializedName("pm25_local_fraction_heating")
    val pm25LocalFractionHeating: VarDataModel? = null,
    @SerializedName("pm25_local_fraction_industry")
    val pm25LocalFractionIndustry: VarDataModel? = null,
    @SerializedName("pm25_local_fraction_shipping")
    val pm25LocalFractionShipping: VarDataModel? = null,
    @SerializedName("pm25_local_fraction_traffic_exhaust")
    val pm25LocalFractionTrafficExhaust: VarDataModel? = null,
    @SerializedName("pm25_local_fraction_traffic_nonexhaust")
    val pm25LocalFractionTrafficNonexhaust: VarDataModel? = null,
    @SerializedName("pm25_nonlocal_fraction")
    val pm25NonlocalFraction: VarDataModel? = null
)

data class AreaClassModel(
    val desc: String? = null,
    val name: String? = null
)

data class LocationModel(
    val areaclass: String? = null,
    val areacode: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val name: String? = null,
    val superareacode: String? = null
)

data class MetaModel(
    val location: LocationModel? = null,
    val reftime: String? = null,
    val sublocations: List<LocationModel>? = null,
    val superlocation: LocationModel? = null
)

data class MeteoDescriptionModel(val variables: MeteoVariablesModel? = null)

data class MeteoVariableModel(
    @SerializedName("nameNO")
    val nameno: String? = null,
    @SerializedName("range_base")
    val rangeBase: Double? = null,
    @SerializedName("range_max")
    val rangeMax: Double? = null,
    @SerializedName("range_min")
    val rangeMin: Double? = null,
    val units: String? = null
)

data class MeteoVariablesModel(
    @SerializedName("air_temperature_0m")
    val airTemperature0m: MeteoVariableModel? = null,
    @SerializedName("air_temperature_100m")
    val airTemperature100m: MeteoVariableModel? = null,
    @SerializedName("air_temperature_200m")
    val airTemperature200m: MeteoVariableModel? = null,
    @SerializedName("air_temperature_20m")
    val airTemperature20m: MeteoVariableModel? = null,
    @SerializedName("air_temperature_2m")
    val airTemperature2m: MeteoVariableModel? = null,
    @SerializedName("air_temperature_500m")
    val airTemperature500m: MeteoVariableModel? = null,
    @SerializedName("atmosphere_boundary_layer_thickness")
    val atmosphereBoundaryLayerThickness: MeteoVariableModel? = null,
    @SerializedName("cloud_area_fraction")
    val cloudAreaFraction: MeteoVariableModel? = null,
    @SerializedName("integral_of_surface_net_downward_radiation_flux_wrt_time")
    val integralOfSurfaceNetDownwardRadiationFluxWrtTime: MeteoVariableModel? = null,
    @SerializedName("rainfall_amount")
    val rainfallAmount: MeteoVariableModel? = null,
    @SerializedName("relative_humidity_2m")
    val relativeHumidity2m: MeteoVariableModel? = null,
    @SerializedName("snowfall_amount")
    val snowfallAmount: MeteoVariableModel? = null,
    @SerializedName("surface_air_pressure")
    val surfaceAirPressure: MeteoVariableModel? = null,
    @SerializedName("wind_direction")
    val windDirection: MeteoVariableModel? = null,
    @SerializedName("wind_speed")
    val windSpeed: MeteoVariableModel? = null
)

data class RefTimeModel(val reftimes: List<String>? = null)

data class SimpleLocationModel(
    val areacode: String? = null,
    val name: String? = null
)

data class StationModel(
    val delomrade: SimpleLocationModel? = null,
    val eoi: String? = null,
    val grunnkrets: SimpleLocationModel? = null,
    val height: Double? = null,
    val kommune: SimpleLocationModel? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val name: String? = null
)

data class VarDataModel(
    val units: String? = null,
    val value: Double? = null
)
