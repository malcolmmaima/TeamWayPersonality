package com.malcolmmaima.teamwaypersonality.ui.personalitytest.repository

import com.google.gson.Gson
import com.malcolmmaima.teamwaypersonality.data.datastore.AppDatasource
import com.malcolmmaima.teamwaypersonality.data.models.PersonalityQuestionsResponse
import com.malcolmmaima.teamwaypersonality.data.repository.AppRepositoryImpl
import com.malcolmmaima.teamwaypersonality.network.APIResource
import com.malcolmmaima.teamwaypersonality.network.MalcolmApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class AppRepositoryImplTest {
    private lateinit var appRepository: AppRepositoryImpl
    private val malcolmApi = mockk<MalcolmApi>()
    private val userPrefs = mockk<AppDatasource>()
    private val gson = Gson()

    @Before
    fun setup() {
        appRepository = AppRepositoryImpl(malcolmApi, userPrefs, gson)
    }

    @Test
    fun `getPersonalityQuestions returns success result`() = runTest {
        val expectedResponse = PersonalityQuestionsResponse(listOf(/* Your data here */))
        coEvery { malcolmApi.getPersonalityQuestions() } returns expectedResponse

        val actualResponse = appRepository.getPersonalityQuestions()

        //assert that the result is of type success and has the expected value
        assertEquals(APIResource.Success(expectedResponse), actualResponse)
    }

    @Test
    fun `getPersonalityQuestions returns error result`() = runTest {
        //mock API error response
        val errorCode = 404
        val errorResponse = Response.error<PersonalityQuestionsResponse>(
            errorCode,
            ResponseBody.create(null, "")
        )
        coEvery { malcolmApi.getPersonalityQuestions() } throws HttpException(errorResponse)

        //call the repository function
        val actualResponse = appRepository.getPersonalityQuestions()

        //assert
        val expectedError = APIResource.Error(false, errorCode, null)
        assertEquals(expectedError.isNetworkError, (actualResponse as APIResource.Error).isNetworkError)
        assertEquals(expectedError.errorCode, actualResponse.errorCode)
    }

}
