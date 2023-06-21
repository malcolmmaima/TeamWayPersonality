package com.malcolmmaima.teamwaypersonality.ui.personalitytest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.malcolmmaima.teamwaypersonality.data.models.Option
import com.malcolmmaima.teamwaypersonality.data.models.PersonalityQuestionsResponse
import com.malcolmmaima.teamwaypersonality.data.models.Question
import com.malcolmmaima.teamwaypersonality.data.models.Traits
import com.malcolmmaima.teamwaypersonality.data.repository.AppRepository
import com.malcolmmaima.teamwaypersonality.network.APIResource
import com.malcolmmaima.teamwaypersonality.utils.CoroutineCustomRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val customCoroutineRule = CoroutineCustomRule()

    private lateinit var viewModel: MainViewModel
    private val appRepository = mockk<AppRepository>(relaxed = true)

    @Before
    fun setup() {
        viewModel = MainViewModel(appRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `fetchPersonalityQuestions emits success result`() = runTest {
        // Mock APIResource.Success response
        val expectedResponse = APIResource.Success(
            PersonalityQuestionsResponse(
                listOf(
                    Question(
                        id = "1",
                        question = "Personality Question 1",
                        options = listOf(
                            Option(
                                id = "A",
                                text = "Option A",
                                traits = Traits(
                                    openness = 0.0,
                                    conscientiousness = 0.0,
                                    extraversion = 0.0,
                                    agreeableness = 0.0,
                                    neuroticism = 0.0
                                )
                            )
                        )
                    )
                )
            )
        )

        coEvery { appRepository.getPersonalityQuestions() } returns expectedResponse

        viewModel.fetchPersonalityQuestions()

        // Collect the emitted value from the shared flow
        val actualResponse = viewModel.personalityQuestions

        // Assert that the actual response is equal to the expected response
        assert(APIResource.Success(actualResponse.first()).value == expectedResponse.value)
    }

    // test fetch personality throws error
    @Test
    fun `fetchPersonalityQuestions emits network error result`() = runTest {
        // Mock APIResource.Error response
        val expectedResponse = APIResource.Error(true, errorCode = 500, errorBody = "Error Message".toResponseBody(
            null
        )
        )

        coEvery { appRepository.getPersonalityQuestions() } returns expectedResponse

        viewModel.fetchPersonalityQuestions()

        // Collect the emitted value from live data and assert that error message is equal to the expected error message
        viewModel.errorMessage.observeForever {
            assert(it == "Error Message")
        }
    }
}
