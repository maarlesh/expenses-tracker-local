package com.local_expenses.presentation.ui.creation_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.local_expenses.presentation.theme.MontserratFontFamily
import com.local_expenses.presentation.ui.common.AppButton

@Composable
fun CreateCategory(
    viewModel: CreationScreenViewModel,
    userId : Int,
){
    var amountInput by remember { mutableStateOf("") }
    Column(
    ){
        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Category", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp, fontFamily = MontserratFontFamily, color = MaterialTheme.colorScheme.primary)
        )

        Spacer(Modifier.height(32.dp))
        AppButton(
            onClick = {
                viewModel.createCategory(
                    amountInput,
                    userId,
                )
            },
            enabled = amountInput != "",
            text = "Create",
            modifier = Modifier.fillMaxWidth()
        )
    }
}