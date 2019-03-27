package no.uio.ifi.in2000.gruppe55

class Element(var name: String, var desc: String)

object Supplier{
    val elements = arrayListOf<Element>(
        Element("Task1", "Description1"),
        Element("Task2", "Description2"),
        Element("Task3", "Description3"))
}
