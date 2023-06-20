package com.malcolmmaima.teamwaypersonality.network

import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

suspend fun <T : Any> safeApiCall(
    apiCall: suspend () -> T,
) : APIResource<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            Timber.tag("BaseRepo").d("safeApiCall: %s", response)
            APIResource.Success(response)
        } catch (throwable: Throwable) {
            when(throwable){
                is HttpException -> {
                    Timber.tag("BaseRepo").e("safeApiCall: %s", throwable.message)
                    APIResource.Error(false, throwable.code(), throwable.response()?.errorBody())
                }
                else -> {
                    Timber.tag("BaseRepo").e("safeApiCall: %s", throwable.message)
                    APIResource.Error(true, null, null)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? = try {
    throwable.response()?.errorBody()?.charStream()?.let {
        val gson = GsonBuilder()
            .create()
        gson.fromJson(it, ErrorResponse::class.java)
    }
} catch (exception: Exception) {
    Timber.e(exception)
    null
}