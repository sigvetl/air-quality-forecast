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

data class Value (val name: String, val value: Double?, val unit: String?)

object sListe{
    var values = mutableListOf(
        Value("Stov", 2.0, "1")
    )
}