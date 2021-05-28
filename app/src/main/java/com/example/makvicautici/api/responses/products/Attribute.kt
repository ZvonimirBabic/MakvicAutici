package com.example.makvicautici.api.responses.products

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attribute(
    val id: Int,
    val name: String,
    val options: List<String>,
    val position: Int,
    val variation: Boolean,
    val visible: Boolean
) : Parcelable