package com.example.quizify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizify.data.datastore.DataStoreManager
import com.example.quizify.data.model.User
import com.example.quizify.data.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    init {
        viewModelScope.launch {
            dataStoreManager.isLoggedInFlow.collect {
                _isLoggedIn.value = it
            }
        }
    }

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreManager.userEmailFlow.collect { email ->
                email?.let {
                    val user = quizRepository.getUserByEmail(it)
                    _user.value = user
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = quizRepository.getUserByEmailAndPassword(email, password)
            if (user != null) {
                dataStoreManager.saveLoginState(true, user.mail)
                _authState.value = AuthState.Success
            } else {
                _authState.value = AuthState.Error("Either email or password is wrong")
            }
        }
    }

    fun signup(name: String, surname: String, email: String, password: String) {
        viewModelScope.launch {
            val exists = quizRepository.getUserByEmail(email)
            if (exists == null) {
                quizRepository.insertUser(User(name = name, surname = surname, mail = email, password = password))
                dataStoreManager.saveLoginState(false, email)
                _authState.value = AuthState.Success
            } else {
                _authState.value = AuthState.Error("User already exists")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.saveLoginState(false)
            _user.value = null
            _authState.value = AuthState.Idle
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}
