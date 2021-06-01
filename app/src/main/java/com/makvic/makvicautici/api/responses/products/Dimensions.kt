package com.makvic.makvicautici.api.responses.products

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dimensions(
    val height: String,
    val length: String,
    val width: String
) : Parcelable