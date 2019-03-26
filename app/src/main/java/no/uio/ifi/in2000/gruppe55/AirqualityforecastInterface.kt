package no.uio.ifi.in2000.gruppe55

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

/**
 * AirqualityforecastInterface provides an abstract interface that represents
 * [Airqualityforecast](https://in2000-apiproxy.ifi.uio.no/weatherapi/airqualityforecast/0.1/documentation).
 *
 * Each of the routes from the API definition of Airqualityforecast is replicated in a relatively similar form; there is
 * no magic or other tricks. To get more thorough documentation, consider checking the API of Airqualityforecast.
 *
 * To keep methods general, consider accepting any `(airqualityforecast : [AirqualityforecastInterface]` in order to
 * admit easy testing and extensibility.
 */
interface AirqualityforecastInterface {
    suspend fun main(
        areaclass: String? = null,
        lat: Double? = null,
        lon: Double? = null,
        reftime: String? = null,
        show: String? = null,
        station: String? = null
    ): AirQualityLocationModel

    suspend fun aqiDescription(): AQIDescriptionModel

    suspend fun areaclasses(): List<AreaClassModel>

    suspend fun areas(areaclass: String? = null): List<LocationModel>

    suspend fun healthz(): ResponseBody

    suspend fun met(
        reftime: String? = null,
        station: String
    ): AirQualityLocationModel

    suspend fun metDescription(): MeteoDescriptionModel

    suspend fun reftimes(): List<RefTimeModel>

    suspend fun stations(): List<StationModel>

    suspend fun values(): ResponseBody
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
