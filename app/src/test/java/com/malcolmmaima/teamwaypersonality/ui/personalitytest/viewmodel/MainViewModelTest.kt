package com.malcolmmaima.teamwaypersonality.ui.personalitytest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.malcolmmaima.teamwaypersonality.data.models.PersonalityQuestionsResponse
import com.malcolmmaima.teamwaypersonality.data.repository.AppRepository
import com.malcolmmaima.teamwaypersonality.network.APIResource
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

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

//    @Test
//    fun `fetchPersonalityQuestions emits success result`() = runTest {
//        // Mock APIResource.Success response
//        coEvery { appRepository.getPersonalityQuestions() } returns APIResource.Success(
//            PersonalityQuestionsResponse(
//                listOf()
//            )
//        )
//
//        viewModel.fetchPersonalityQuestions()
//
//        // Assert that the emitted value is the expected one
//    }
}
