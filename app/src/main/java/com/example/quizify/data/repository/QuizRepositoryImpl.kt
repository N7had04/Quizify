package com.example.quizify.data.repository

import com.example.quizify.data.api.QuizifyService
import com.example.quizify.data.model.ApiResponse
import retrofit2.Response

class QuizRepositoryImpl(private val quizifyService: QuizifyService): QuizRepository {
    override suspend fun getQuestionsByDifficultyAndCategory(
        difficulty: String,
        category: String
    ): Response<ApiResponse> {
        return quizifyService.getQuestionsByDifficultyAndCategory(difficulty = difficulty, category = category)
    }
}