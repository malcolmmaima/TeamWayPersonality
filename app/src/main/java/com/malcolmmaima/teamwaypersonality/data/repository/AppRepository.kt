package com.malcolmmaima.teamwaypersonality.data.repository

import com.malcolmmaima.teamwaypersonality.data.models.PersonalityQuestionsResponse
import com.malcolmmaima.teamwaypersonality.network.APIResource

interface AppRepository {
    suspend fun getPersonalityQuestions(): APIResource<PersonalityQuestionsResponse>
}