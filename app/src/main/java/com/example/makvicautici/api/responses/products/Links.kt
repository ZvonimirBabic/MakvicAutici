package com.example.makvicautici.api.responses.products

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Links(
    val collection: List<Collection>,
    val self: List<Self>
) : Parcelable