package com.malcolmmaima.teamwaypersonality.domain.repository

import com.malcolmmaima.teamwaypersonality.data.models.PersonalityQuestionsResponse
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getPersonalityQuestions(): Flow<List<PersonalityQuestionsResponse>>
}