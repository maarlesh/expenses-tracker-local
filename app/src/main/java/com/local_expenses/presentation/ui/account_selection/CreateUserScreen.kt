package com.local_expenses.presentation.ui.account_selection

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.local_expenses.presentation.theme.AppGradientBrush2
import com.local_expenses.presentation.ui.common.AppButton
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.local_expenses.presentation.theme.MontserratFontFamily

@Composable
fun CreateUserScreen(
    viewModel : ProfileSelectionViewModel = hiltViewModel(),
    navController : NavController
){
    val createdUserIdFlow = remember { viewModel.createdUserId }
    val context = LocalContext.current

    LaunchedEffect(createdUserIdFlow) {
        createdUserIdFlow.collect { userId ->
            // Navigate once user is created
            navController.navigate("home/$userId") {
                // Optional: clear backstack so user can't go back to create screen
                popUpTo("profile_selection") { inclusive = true }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppGradientBrush2)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .background(Color.White.copy(alpha = 0.18f), RoundedCornerShape(32.dp))
                .border(
                    1.5.dp,
                    Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.7f),
                            Color.White.copy(alpha = 0.15f)
                        )
                    ),
                    RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 28.dp, vertical = 26.dp)
        ) {
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            OutlinedTextField(
                value = username,
                onValueChange = { input ->
                    if (!input.contains(' ')) {
                        username = input
                    }
                },
                label = { Text("Username", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Text
                ),
                textStyle =TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = MontserratFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password input - show obscured text
            OutlinedTextField(
                value = password,
                onValueChange = {input ->
                    password = input
                },
                label = { Text("Password", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    capitalization = KeyboardCapitalization.None
                ),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = MontserratFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
            )

            Spacer(Modifier.height(32.dp))
            AppButton(
                onClick = {
                    viewModel.createUser(username, password)
                },
                enabled = username.isNotEmpty() && password.isNotEmpty(),
                text = "Create User",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}