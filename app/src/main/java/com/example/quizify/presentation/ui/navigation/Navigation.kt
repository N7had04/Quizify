package com.example.quizify.presentation.ui.navigation

import android.app.Activity
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizify.data.model.User
import com.example.quizify.presentation.ui.screens.ErrorScreen
import com.example.quizify.presentation.ui.screens.GameEndScreen
import com.example.quizify.presentation.ui.screens.GameScreen
import com.example.quizify.presentation.ui.screens.HomeScreen
import com.example.quizify.presentation.ui.screens.LoadingScreen
import com.example.quizify.presentation.ui.screens.LoginScreen
import com.example.quizify.presentation.ui.screens.SignUpScreen
import com.example.quizify.presentation.ui.screens.SplashScreen
import com.example.quizify.presentation.viewmodel.AuthViewModel
import com.example.quizify.presentation.viewmodel.QuizViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel(),
    quizViewModel: QuizViewModel = hiltViewModel()
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val authState by authViewModel.authState.collectAsState()
    val user by authViewModel.user.collectAsState()
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val questions by quizViewModel.questions.collectAsState()
    val error by quizViewModel.error.collectAsState()
    val isLoading by quizViewModel.isLoading.collectAsState()
    val splashScreenShown = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500)
        splashScreenShown.value = true
    }

    LaunchedEffect(isLoggedIn, splashScreenShown.value) {
        if (splashScreenShown.value && isLoggedIn != null) {
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            when (isLoggedIn) {
                true -> {
                    if (currentRoute in listOf("login", "signup", "splash")) {
                        navController.navigate("home") {
                            popUpTo("splash") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
                false -> {
                    if (currentRoute != "login") {
                        navController.navigate("login") {
                            popUpTo("splash") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
                else -> {}
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(modifier = modifier)
        }

        composable("login") {
            BackHandler {}
            LoginScreen(
                authState = authState,
                login = { email, password -> authViewModel.login(email, password) },
                navigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    } },
                email = quizViewModel.email,
                password = quizViewModel.password,
                passwordVisibility = quizViewModel.passwordVisibility,
                context = context,
                isPortrait = isPortrait,
                modifier = modifier
            )
        }

        composable("signup") {
            BackHandler {
                navController.popBackStack("login", false)
            }
            SignUpScreen(
                authState = authState,
                signUp = { name, surname, email, password -> authViewModel.signup(name, surname, email, password) },
                navigate = { route -> navController.popBackStack(route, false) },
                name = quizViewModel.name,
                nameError = quizViewModel.nameError,
                surname = quizViewModel.surname,
                surnameError = quizViewModel.surnameError,
                email = quizViewModel.email,
                emailError = quizViewModel.emailError,
                password = quizViewModel.password,
                passwordError = quizViewModel.passwordError,
                password2 = quizViewModel.password2,
                password2Error = quizViewModel.password2Error,
                passwordVisibility = quizViewModel.passwordVisibility,
                passwordVisibility2 = quizViewModel.passwordVisibility2,
                context = context,
                modifier = modifier
            )
        }

        composable("home") {
            BackHandler {
                (context as? Activity)?.finish()
            }
            HomeScreen(
                user = user ?: User(0, "", "", "", ""),
                onStartGame = { difficulty, categoryID -> navController.navigate("game/$difficulty/$categoryID") },
                onLogout = { authViewModel.logout() },
                difficultyOptions = quizViewModel.difficultyOptions,
                selectedDifficulty = quizViewModel.selectedDifficulty,
                categoryOptions = quizViewModel.categoryOptions,
                selectedCategory = quizViewModel.selectedCategory,
                expanded = quizViewModel.expanded,
                expanded2 = quizViewModel.expanded2,
                showSheet = quizViewModel.showSheet,
                isPortrait = isPortrait,
                modifier = modifier,
                coroutineScope = rememberCoroutineScope(),
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            )

        }

        composable("game/{difficulty}/{categoryID}") { backStackEntry ->
            val difficulty = backStackEntry.arguments?.getString("difficulty")!!
            val category = backStackEntry.arguments?.getString("categoryID")?.toIntOrNull() ?: 9

            LaunchedEffect(Unit) {
                quizViewModel.getQuestions(difficulty, category)
            }

            when {
                isLoading -> {
                    LoadingScreen(
                        modifier = modifier
                    )
                }

                error != null -> {
                    ErrorScreen(
                        error = error,
                        getQuestions = { quizViewModel.getQuestions(difficulty, category)},
                        modifier = modifier
                    )
                }

                questions.isNotEmpty() -> {
                    GameScreen(
                        questions = questions,
                        timer = quizViewModel.timer,
                        description = quizViewModel.description,
                        selectedAnswer = quizViewModel.selectedAnswer,
                        currentQuestion = quizViewModel.currentQuestion,
                        score = quizViewModel.score,
                        showQuitDialog = quizViewModel.showQuitDialog,
                        modifier = modifier,
                        onQuit = { navController.popBackStack("home", false) },
                        navigateToEnd = { navController.navigate("end/$it") {
                            popUpTo("game/{difficulty}/{categoryID}") { inclusive = true }
                        }}
                    )
                }
                else -> {
                    LoadingScreen(
                        modifier = modifier
                    )
                }
            }
        }

        composable("end/{score}") {
            val score = it.arguments?.getString("score")?.toIntOrNull() ?: 0
            BackHandler {
                navController.popBackStack("home", false)
            }
            GameEndScreen(
                score = score,
                isPortrait = isPortrait,
                modifier = modifier,
                navigateToHome = { navController.popBackStack("home", false)}
            )
        }
    }
}