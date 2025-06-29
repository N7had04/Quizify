package com.example.quizify.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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

    val name = mutableStateOf("")
    val surname = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val password2 = mutableStateOf("")
    val passwordVisibility = mutableStateOf(false)
    val passwordVisibility2 = mutableStateOf(false)
    val nameError = mutableStateOf(false)
    val surnameError = mutableStateOf(false)
    val emailError = mutableStateOf(false)
    val passwordError = mutableStateOf(false)
    val password2Error = mutableStateOf(false)

    val expanded = mutableStateOf(false)
    val expanded2 = mutableStateOf(false)
    val difficultyOptions = mapOf("Easy" to "easy", "Medium" to "medium", "Hard" to "hard")
    val categoryOptions = mapOf("General Knowledge" to 9, "Books" to 10, "Film" to 11, "Music" to 12,
        "Musicals & Theatres" to 13, "Television" to 14, "Video Games" to 15, "Board Games" to 16,
        "Science & Nature" to 17, "Computers" to 18, "Mathematics" to 19, "Mythology" to 20,
        "Sports" to 21, "Geography" to 22, "History" to 23, "Politics" to 24, "Art" to 25,
        "Celebrities" to 26, "Animals" to 27, "Vehicles" to 28, "Comics" to 29, "Gadgets" to 30,
        "Japanese Anime & Manga" to 31, "Cartoon & Animation" to 32)
    val selectedDifficulty = mutableStateOf(difficultyOptions.keys.first())
    var selectedCategory = mutableStateOf(categoryOptions.keys.first())
    val showSheet = mutableStateOf(false)

    val timer = mutableIntStateOf(30)
    val description = mutableStateOf("")
    val selectedAnswer = mutableStateOf("")
    val currentQuestion = mutableIntStateOf(0)
    val score = mutableIntStateOf(0)
    val showQuitDialog = mutableStateOf(false)

    fun getQuestions(difficulty: String, category: Int) {
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