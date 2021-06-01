package com.makvic.makvicautici.api.responses.products

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
    val id: Int,
    val name: String,
    val slug: String
) : Parcelable