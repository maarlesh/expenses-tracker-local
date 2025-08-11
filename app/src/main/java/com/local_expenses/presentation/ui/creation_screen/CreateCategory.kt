package com.local_expenses.presentation.ui.creation_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.local_expenses.presentation.theme.AppGradientBrush2
import com.local_expenses.presentation.theme.MontserratFontFamily
import com.local_expenses.presentation.ui.common.AppButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreateCategory(
    viewModel: CreationScreenViewModel,
    userId: Int,
) {
    var amountInput by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(AppGradientBrush2)
        ) {
            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = amountInput,
                onValueChange = { amountInput = it },
                label = { Text("Category", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = MontserratFontFamily,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(Modifier.height(32.dp))
            AppButton(
                onClick = {
                    viewModel.createCategory(amountInput, userId)
                    amountInput = ""
                    scope.launch {
                        snackbarHostState.showSnackbar("Category added successfully")
                    }
                },
                enabled = amountInput != "",
                text = "Create",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}