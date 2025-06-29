package com.example.quizify.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quizify.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}