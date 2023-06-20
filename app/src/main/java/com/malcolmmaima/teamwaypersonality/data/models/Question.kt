package com.malcolmmaima.teamwaypersonality.data.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Question(
    val options: List<Option>,
    val question: String
) : Parcelable