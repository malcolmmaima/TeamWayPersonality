package com.malcolmmaima.teamwaypersonality.data.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class PersonalityQuestionsResponse(
    val questions: List<Question>
) : Parcelable