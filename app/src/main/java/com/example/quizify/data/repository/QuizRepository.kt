package com.example.quizify.data.repository

import com.example.quizify.data.model.ApiResponse
import com.example.quizify.data.model.User
import retrofit2.Response


interface QuizRepository {
    suspend fun getQuestionsByDifficultyAndCategory(difficulty: String, category: Int): Response<ApiResponse>
    suspend fun insertUser(user: User)
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?
    suspend fun getUserByEmail(email: String): User?
}