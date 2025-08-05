package com.local_expenses.presentation.ui.creation_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.local_expenses.presentation.theme.AppGradientBrush2
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.local_expenses.presentation.theme.MontserratFontFamily

enum class CreationTab {
    Income, Expense, Transfer, Category
}

@Composable
fun CreationScreen(
    navController: NavController,
    viewModel: CreationScreenViewModel
) {
    var selectedTab by remember { mutableStateOf(CreationTab.Income) }

    Box(
        modifier = Modifier
            .background(AppGradientBrush2)
            .systemBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize(),
        ) {
            // Tab Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CreationTabItem(
                    label = "Income",
                    isSelected = selectedTab == CreationTab.Income,
                    onClick = { selectedTab = CreationTab.Income }
                )
                CreationTabItem(
                    label = "Expense",
                    isSelected = selectedTab == CreationTab.Expense,
                    onClick = { selectedTab = CreationTab.Expense }
                )
                CreationTabItem(
                    label = "Transfer",
                    isSelected = selectedTab == CreationTab.Transfer,
                    onClick = { selectedTab = CreationTab.Transfer }
                )
                CreationTabItem(
                    label = "Category",
                    isSelected = selectedTab == CreationTab.Category,
                    onClick = { selectedTab = CreationTab.Category }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                CreationTab.Income -> IncomeContent(viewModel)
                CreationTab.Expense -> ExpenseContent(viewModel)
                CreationTab.Transfer -> TransferContent(viewModel)
                CreationTab.Category -> TransferContent(viewModel)
            }
        }
    }
}

@Composable
fun CreationTabItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected)
        Color.White.copy(alpha = 0.6f)
    else
        Color.White.copy(alpha = 0.25f)

    val borderColor = if (isSelected)
        MaterialTheme.colorScheme.primary
    else
        Color.White.copy(alpha = 0.12f)

    Box(
        modifier = Modifier
            .background(backgroundColor,)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(borderColor, borderColor)
                ),
                shape = RectangleShape
            )
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            maxLines = 1
        )
    }
}

@Composable
fun IncomeContent(
    viewModel: CreationScreenViewModel,
    accounts: List<String> = listOf("Savings", "Cash", "Credit Card"), // replace with your accounts list
    categories: List<String> = listOf("💰", "🎉", "🏆", "💼") // emoji categories
) {
    var selectedAccount by remember { mutableStateOf(accounts.firstOrNull() ?: "") }
    var expandedAccount by remember { mutableStateOf(false) }

    var amountInput by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(categories.firstOrNull() ?: "") }
    var expandedCategory by remember { mutableStateOf(false) }

    var description by remember { mutableStateOf("") }

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
        AccountDropdown(
            accounts = accounts,
            selectedAccount = selectedAccount,
            onAccountSelected = { selectedAccount = it }
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it.filter { c -> c.isDigit() || c == '.' } }, // allow digits and decimal
            label = { Text("Amount", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp, fontFamily = MontserratFontFamily)
        )

        Spacer(Modifier.height(12.dp))

        Text(text = "Category", color = Color.White, style = MaterialTheme.typography.bodyMedium)
        Box {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedCategory = true },
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        onClick = {expandedCategory = !expandedCategory}
                    ) {
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
                modifier =  Modifier.background(
                    AppGradientBrush2
                )
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            expandedCategory = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Description
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = Color.Magenta,
//                unfocusedBorderColor = Color.White.copy(alpha = 0.4f),
//                containerColor = Color.White.copy(alpha = 0.1f),
//                textColor = Color.White,
//                cursorColor = Color.Magenta
//            ),
            maxLines = 3,
            textStyle = TextStyle(fontSize = 16.sp, fontFamily = MontserratFontFamily)
        )

        Spacer(Modifier.height(24.dp))

        // Add Button
        Button(
            onClick = {
                // Your logic to add income data here
                // e.g. viewModel.addIncome(selectedAccount, amountInput, selectedCategory, description)
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

@Composable
fun ExpenseContent(viewModel: CreationScreenViewModel) {
    // TODO: Your expense creation UI goes here
    Text(
        "Expense form or content here",
        modifier = Modifier.padding(16.dp),
        color = Color.White
    )
}

@Composable
fun TransferContent(viewModel: CreationScreenViewModel) {
    // TODO: Your transfer creation UI goes here
    Text(
        "Transfer form or content here",
        modifier = Modifier.padding(16.dp),
        color = Color.White
    )
}


@Composable
fun AccountDropdown(
    accounts: List<String>,
    selectedAccount: String,
    onAccountSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(

    ) {
        OutlinedTextField(
            value = selectedAccount,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            readOnly = true,
            label = { Text("Select Account", color = Color.White) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown",
                        tint = Color.White
                    )
                }
            },
            singleLine = true,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier =  Modifier.background(
                AppGradientBrush2
            )
        ) {
            accounts.forEach { account ->
                DropdownMenuItem(
                    text = { Text(account) },
                    onClick = {
                        onAccountSelected(account)
                        expanded = false
                    }
                )
            }
        }
    }
}
