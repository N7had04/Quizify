package com.example.quizify.data.repository

import com.example.quizify.data.api.QuizifyService
import com.example.quizify.data.db.UserDao
import com.example.quizify.data.model.ApiResponse
import com.example.quizify.data.model.User
import retrofit2.Response

class QuizRepositoryImpl(
    private val quizifyService: QuizifyService,
    private val userDao: UserDao
): QuizRepository {

    override suspend fun getQuestionsByDifficultyAndCategory(difficulty: String, category: Int): Response<ApiResponse> {
        return quizifyService.getQuestionsByDifficultyAndCategory(difficulty = difficulty, category = category)
    }

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

}