package com.malcolmmaima.teamwaypersonality.data.repository

import com.google.gson.Gson
import com.malcolmmaima.teamwaypersonality.data.datastore.AppDatasource
import com.malcolmmaima.teamwaypersonality.network.APIResource
import com.malcolmmaima.teamwaypersonality.network.MalcolmApi
import com.malcolmmaima.teamwaypersonality.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val malcolmApi: MalcolmApi,
    private val userPrefs: AppDatasource,
    val gson: Gson,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AppRepository {

    override suspend fun getPersonalityQuestions() = safeApiCall {
        val response = malcolmApi.getPersonalityQuestions()
        withContext(dispatcher) {
            response.let {
                // maybe save to db
            }
        }
        return@safeApiCall response
    }

}