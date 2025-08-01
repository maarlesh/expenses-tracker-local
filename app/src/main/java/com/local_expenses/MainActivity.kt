package com.local_expenses

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.local_expenses.data.local.entity.UserEntity
import com.local_expenses.presentation.theme.LocalexpensesTheme
import com.local_expenses.presentation.ui.account_selection.ProfileSelectionScreen
import com.local_expenses.presentation.ui.account_selection.ProfileSelectionViewModel
import com.local_expenses.presentation.ui.home_screen.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalexpensesTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "profile_selection",
                    ) {
                        composable("profile_selection") {
                            ProfileSelectionRoute(
                                navController = navController,
                                onProfileSelected = { selectedUser ->
                                    Log.d("SelectedProfile", "Selected user: ${selectedUser.name}")
                                    navController.navigate("home/${selectedUser.userId}")
                                }
                            )
                        }

                        composable(
                            route = "home/{userId}",
                            arguments = listOf(navArgument("userId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                            val viewModel: ProfileSelectionViewModel = hiltViewModel()

                            LaunchedEffect(userId) {
                                viewModel.setSelectedUserId(userId)
                            }

                            val accounts by viewModel.account.collectAsState()

                            HomeScreen(accounts = accounts)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileSelectionRoute(
    viewModel: ProfileSelectionViewModel = hiltViewModel(),
    navController: NavController,
    onProfileSelected: (UserEntity) -> Unit
) {
    val users by viewModel.accounts.collectAsState(emptyList())

    ProfileSelectionScreen(
        users = users,
        onNextClick = { selectedUser: UserEntity ->
            Log.d("SelectedProfile", "Selected user: ${selectedUser.name}")
            viewModel.setSelectedUserId(selectedUser.userId)
            navController.navigate("home/${selectedUser.userId}")
        }
    )
}
