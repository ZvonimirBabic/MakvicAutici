package com.example.makvicautici.api.responses.manufacturers

data class Manufacturer(
    val id: Int?,
    val name: String,
    val slug: String?
) {
    override fun toString(): String {
        return name
    }
}