package no.uio.ifi.in2000.gruppe55

data class Stations(
    val delomrade: Delomrade,
    val eoi: String,
    val grunnkrets: Grunnkrets,
    val height: Double,
    val kommune: Kommune,
    val latitude: String,
    val longitude: String,
    val name: String
)

data class Grunnkrets(
    val areacode: String,
    val name: String
)

data class Delomrade(
    val areacode: String,
    val name: String
)

data class Kommune(
    val areacode: String,
    val name: String
)