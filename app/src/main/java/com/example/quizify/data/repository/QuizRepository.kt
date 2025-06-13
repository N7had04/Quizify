package com.example.quizify.data.repository

import com.example.quizify.data.model.ApiResponse
import retrofit2.Response


interface QuizRepository {
    suspend fun getQuestionsByDifficultyAndCategory(difficulty: String, category: String): Response<ApiResponse>
}