package com.yongji.weatherzip.network

import android.net.Uri
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherResponse (
    @field:Json(name = "observations")
    val observations: Observations,
    @field:Json(name = "metric")
    val metric: Boolean
) : Parcelable

@Parcelize
data class Observations(
    @field:Json(name = "location")
    val location: List<Location>
) : Parcelable

@Parcelize
data class Location(
    @field:Json(name = "observation")
    val observation: List<Observation>
) : Parcelable


@Parcelize
data class Observation(
    @Json(name = "city") val city: String,
    @Json(name = "state") val state: String,
    @Json(name = "iconLink") val iconLink: String,
    @Json(name = "temperature") val temperature: String,
    @Json(name = "description") val description: String


) : Parcelable