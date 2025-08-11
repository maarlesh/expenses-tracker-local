package com.local_expenses.presentation.ui.creation_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.local_expenses.data.local.entity.AccountEntity
import com.local_expenses.data.local.entity.CategoryEntity
import com.local_expenses.presentation.theme.AppGradientBrush2
import com.local_expenses.presentation.theme.MontserratFontFamily
import kotlinx.coroutines.launch

@Composable
fun CreateIncome(
    viewModel: CreationScreenViewModel,
    accounts: List<AccountEntity>,
    categories: List<CategoryEntity>,
) {
    var selectedAccount by remember { mutableStateOf(accounts.firstOrNull()) }
    var expandedAccount by remember { mutableStateOf(false) }

    var amountInput by remember { mutableStateOf("") }

    var selectedCategory by remember { mutableStateOf(categories.firstOrNull()) }
    var expandedCategory by remember { mutableStateOf(false) }

    var description by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(Color.White.copy(alpha = 0.4f), Color.White.copy(alpha = 0.1f))
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        // ACCOUNT DROPDOWN
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedAccount = true }
        ) {
            OutlinedTextField(
                value = selectedAccount?.accountName ?: "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                label = { Text("Select Account", color = Color.White) },
                trailingIcon = {
                    IconButton(onClick = { expandedAccount = !expandedAccount }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown",
                            tint = Color.White
                        )
                    }
                },
                singleLine = true
            )
            DropdownMenu(
                expanded = expandedAccount,
                onDismissRequest = { expandedAccount = false },
                modifier = Modifier.background(AppGradientBrush2)
            ) {
                accounts.forEach { account ->
                    DropdownMenuItem(
                        text = { Text(account.accountName) },
                        onClick = {
                            selectedAccount = account
                            expandedAccount = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // AMOUNT
        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it.filter { c -> c.isDigit() || c == '.' } },
            label = { Text("Amount", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp, fontFamily = MontserratFontFamily, color = MaterialTheme.colorScheme.primary)
        )

        Spacer(Modifier.height(12.dp))

        Text(text = "Category", color = Color.White, style = MaterialTheme.typography.bodyMedium)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedCategory = true }
        ) {
            OutlinedTextField(
                value = selectedCategory?.name ?: "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { expandedCategory = !expandedCategory }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
            DropdownMenu(
                expanded = expandedCategory,
                onDismissRequest = { expandedCategory = false },
                modifier = Modifier.background(AppGradientBrush2)
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            selectedCategory = category
                            expandedCategory = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            textStyle = TextStyle(fontSize = 16.sp, fontFamily = MontserratFontFamily, color = MaterialTheme.colorScheme.primary),
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (selectedAccount != null && selectedCategory != null) {
                    viewModel.addIncome(
                        accountId = selectedAccount!!.accountId,
                        amount = amountInput.toDoubleOrNull() ?: 0.0,
                        categoryId = selectedCategory!!.categoryId,
                        description = description
                    )
                    selectedAccount = accounts[0];
                    amountInput = "";
                    selectedCategory = categories[0];
                    description = "";
                    scope.launch {
                        snackbarHostState.showSnackbar("Income added successfully")
                    }

                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)
        ) {
            Text(text = "Add", color = Color.White)
        }
    }
}