package com.example.quizify.data.model


import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("results")
    val results: List<Question>
)