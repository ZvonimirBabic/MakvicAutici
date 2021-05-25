package com.example.makvicautici.api.responses.makes

data class Make(
    val id: Int?,
    val name: String,
    val slug: String?
) {
    override fun toString(): String {
        return name
    }
}