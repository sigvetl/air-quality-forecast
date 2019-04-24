package no.uio.ifi.in2000.gruppe55


data class Element (val type: Int, val name: String?, var aqi: Double?, var children: List<Element>? = null, var saved: Boolean = false)

object eListe{
    val AREA = 0
    val STATION = 1

    val osloStations = mutableListOf(
        Element(STATION,"Oslo-Øst", 1.2),
        Element(STATION,"Oslo-Vest", 3.4)
    )

    var elementer = mutableListOf(
        Element(AREA,"Porsgrunn", 0.3),
        Element(AREA,"Bergen", 1.4),
        Element(AREA,"Trondheim", 2.2),
        Element(AREA,"Oslo", 3.4, osloStations),
        Element(AREA,"Skien", 5.3)
    )
}

data class Value (val name: String, val nameClass: String, val value: Double?, val unit: String?)

object SListe{
    var values = mutableListOf(
        Value("Stov", "Ozon", 2.0, "1"),
        Value("tull", "Ozon", 1.0, "1"),
        Value("her", "PM2.5", 2.0, "1"),
        Value("sjekk", "PM10", 3.0, "1")
    )
}