package com.malcolmmaima.teamwaypersonality.data.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class PersonalityQuestionsResponse (
    var id: String? = null,
    var question: String? = null,
    var optionA: String? = null,
    var optionB: String? = null,
    var optionC: String? = null,
    var optionD: String? = null,
) : Parcelable