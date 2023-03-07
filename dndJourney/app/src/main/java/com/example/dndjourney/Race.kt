package com.example.dndjourney

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Race(
    val name: String,
    val description: String,
    val photo: Int,
    val full: String
) :Parcelable