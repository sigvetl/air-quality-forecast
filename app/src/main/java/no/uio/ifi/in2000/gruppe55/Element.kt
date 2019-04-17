package no.uio.ifi.in2000.gruppe55

data class Element (val name: String?, val aqi: Double?)
data class Value (val name: String, val value: Double?, val unit: String?)

object eListe{
    var elementer = mutableListOf(
        Element("Porsgrunn", 0.3),
        Element("Bergen", 1.4),
        Element("Trondheim", 2.2),
        Element("Oslo", 3.4),
        Element("Skien", 5.3)
    )
}

object sListe{
    var values = mutableListOf(
        Value("Stov", 2.0, "1")
    )
}