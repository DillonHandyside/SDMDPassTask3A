package com.example.passtask3a

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Medallist(
    val country: String,
    val ioc: String,
    val competed: Int,
    val gold: Int,
    val silver: Int,
    val bronze: Int
) : Parcelable
