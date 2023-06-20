package com.malcolmmaima.teamwaypersonality.data.repository

import com.malcolmmaima.teamwaypersonality.data.datastore.AppDatasource
import com.malcolmmaima.teamwaypersonality.data.models.PersonalityQuestionsResponse
import com.malcolmmaima.teamwaypersonality.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val userPrefs: AppDatasource,
) : AppRepository {

    override suspend fun getPersonalityQuestions(): Flow<List<PersonalityQuestionsResponse>> {
        return flowOf(listOf())
    }
}