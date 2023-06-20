package com.malcolmmaima.teamwaypersonality.ui.personalitytest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malcolmmaima.teamwaypersonality.data.models.PersonalityQuestionsResponse
import com.malcolmmaima.teamwaypersonality.data.repository.AppRepository
import com.malcolmmaima.teamwaypersonality.network.APIResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val appRepository: AppRepository
) : ViewModel() {
    private val _personalityQuestions = MutableSharedFlow<PersonalityQuestionsResponse>()
    val personalityQuestions = _personalityQuestions.asSharedFlow()

    private val _isLoading = MutableSharedFlow<Boolean>()
    val isLoading = _isLoading.asSharedFlow()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchPersonalityQuestions() {
            viewModelScope.launch {
                val personalityQuestions = appRepository.getPersonalityQuestions()
                when (personalityQuestions) {
                    is APIResource.Success -> {
                        _personalityQuestions.emit(personalityQuestions.value)
                        Log.d("MainViewModel", "fetchPersonalityQuestions: ${personalityQuestions.value}")
                    }
                    is APIResource.Loading -> {
                        _isLoading.emit(true)
                    }
                    is APIResource.Error -> {
                        _errorMessage.value = personalityQuestions.errorBody.toString()
                    }
                }
            }
        }
}