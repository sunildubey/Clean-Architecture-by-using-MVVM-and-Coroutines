package com.sunil.covid19globalmeter.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class DashboardModel(
    val Countries: List<Country>,
    val Date: String,
    val Global: Global
)
@Parcelize
data class Country (
    val Country: String,
    val CountryCode: String,
    val Date: String,
    val NewConfirmed: Int,
    val NewDeaths: Int,
    val NewRecovered: Int,
    val Slug: String,
    val TotalConfirmed: Int,
    val TotalDeaths: Int,
    val TotalRecovered: Int
) : Parcelable

data class Global(
    val NewConfirmed: Int,
    val NewDeaths: Int,
    val NewRecovered: Int,
    val TotalConfirmed: Int,
    val TotalDeaths: Int,
    val TotalRecovered: Int
)