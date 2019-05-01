package no.uio.ifi.in2000.gruppe55


data class Element (val type: Int, val name: String?, val stationId: String?, var aqi: Double?,
                    var children: List<Element>? = null, var saved: Boolean = false)

object eListe{
    val AREA = 0
    val STATION = 1


    var elementer = mutableListOf<Element>()
}

data class Value (val name: String, val nameClass: String, val value: Double?, val unit: String?, val intervalModel: List<AQIIntervalModel> = listOf())

object SListe{
    var values = mutableListOf<Value>(
        // Value("Stov", "Ozon", 2.0, "1"),
        // Value("tull", "Ozon", 1.0, "1"),
        // Value("her", "PM2.5", 2.0, "1"),
        // Value("sjekk", "PM10", 3.0, "1")
    )
}