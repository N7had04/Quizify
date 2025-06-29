package com.example.quizify.di

import android.app.Application
import androidx.room.Room
import com.example.quizify.BuildConfig
import com.example.quizify.data.api.QuizifyService
import com.example.quizify.data.datastore.DataStoreManager
import com.example.quizify.data.db.UserDao
import com.example.quizify.data.db.UserDatabase
import com.example.quizify.data.repository.QuizRepository
import com.example.quizify.data.repository.QuizRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideQuizRepository(
        quizifyService: QuizifyService,
        userDao: UserDao
    ): QuizRepository {
        return QuizRepositoryImpl(quizifyService, userDao)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideQuizifyService(retrofit: Retrofit): QuizifyService {
        return retrofit.create(QuizifyService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao
    }

    @Provides
    @Singleton
    fun provideUserDatabase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(app: Application): DataStoreManager {
        return DataStoreManager(app)
    }
}