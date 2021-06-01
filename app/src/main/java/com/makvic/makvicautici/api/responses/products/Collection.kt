package com.makvic.makvicautici.api.responses.products

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Collection(
    val href: String
) : Parcelable