package com.local_expenses

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.local_expenses.data.local.AppDatabase
import com.local_expenses.data.local.entity.UserEntity
import com.local_expenses.presentation.account_selection.ProfileSelectionScreen
import com.local_expenses.presentation.ui.account_selection.ProfileSelectionViewModel
import com.local_expenses.presentation.ui.theme.LocalexpensesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalexpensesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProfileSelectionRoute(
                        onProfileSelected = { selectedUser ->
                            Log.d("SelectedProfile", "Selected user: ${selectedUser.name}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileSelectionRoute(
    viewModel: ProfileSelectionViewModel = hiltViewModel(),
    onProfileSelected: (UserEntity) -> Unit
) {
    val accounts by viewModel.accounts.collectAsState()

    ProfileSelectionScreen(
        users = accounts,
        onNextClick = { selectedUser: UserEntity ->
            Log.d("SelectedProfile", "Selected user: ${selectedUser.name}")
        }
    )
}
