package com.malcolmmaima.teamwaypersonality.data.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Traits(
    val agreeableness: Double,
    val conscientiousness: Double,
    val extraversion: Double,
    val neuroticism: Double,
    val openness: Double
) : Parcelable