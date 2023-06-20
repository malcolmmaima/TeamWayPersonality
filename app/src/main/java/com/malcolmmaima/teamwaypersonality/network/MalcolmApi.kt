package com.malcolmmaima.teamwaypersonality.network

import com.malcolmmaima.teamwaypersonality.data.models.PersonalityQuestionsResponse
import retrofit2.http.HTTP

interface MalcolmApi {
    @HTTP(method = "GET", path = "personality/questions.json", hasBody = false)
    suspend fun getPersonalityQuestions(
    ) : PersonalityQuestionsResponse
}