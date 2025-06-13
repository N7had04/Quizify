package com.example.quizify.data.api

import com.example.quizify.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizifyService {

    @GET("api.php")
    suspend fun getQuestionsByDifficultyAndCategory(
        @Query("amount") amount: Int = 15,
        @Query("type") type: String = "multiple",
        @Query("difficulty") difficulty: String,
        @Query("category") category: String
    ): Response<ApiResponse>
}