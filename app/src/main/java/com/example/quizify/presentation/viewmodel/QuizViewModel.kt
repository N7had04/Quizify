package com.example.quizify.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizify.data.model.Question
import com.example.quizify.data.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val quizRepository: QuizRepository) : ViewModel() {
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun getQuestions(difficulty: String, category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _error.value = null

            try {
                val apiResponse = quizRepository.getQuestionsByDifficultyAndCategory(difficulty, category)
                if (apiResponse.isSuccessful) {
                    _questions.value = apiResponse.body()?.results ?: emptyList()
                    Log.i("Questions", _questions.value.toString())
                } else {
                    _error.value = "Failed to fetch questions"
                }
            } catch (e: Exception) {
                Log.i("Exception", e.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }
}