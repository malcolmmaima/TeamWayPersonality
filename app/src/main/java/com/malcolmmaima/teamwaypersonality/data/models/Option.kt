package com.malcolmmaima.teamwaypersonality.data.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Option(
    val id: String,
    val text: String,
    val traits: Traits
) : Parcelable