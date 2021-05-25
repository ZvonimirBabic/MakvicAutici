package com.example.makvicautici.api.responses.scales

data class Scale (
    val id: Int?,
    val name: String,
    val slug: String?
) {
    override fun toString(): String {
        return name
    }
}