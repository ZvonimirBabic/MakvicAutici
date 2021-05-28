package com.makvic.makvicautici.api.responses.products

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val alt: String,
    val date_created: String,
    val date_modified: String,
    val id: Int,
    val name: String,
    val position: Int,
    val src: String
) : Parcelable