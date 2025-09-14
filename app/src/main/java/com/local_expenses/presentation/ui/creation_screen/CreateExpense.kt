package com.local_expenses.presentation.ui.creation_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
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
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.material3.Typography

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExpense(
    viewModel: CreationScreenViewModel,
    accounts: List<AccountEntity>,
    categories: List<CategoryEntity>,
    onCreated: () -> Unit,
) {
    var selectedAccount by remember { mutableStateOf(accounts.firstOrNull()) }
    var expandedAccount by remember { mutableStateOf(false) }

    var amountInput by remember { mutableStateOf("") }

    var selectedCategory by remember { mutableStateOf(categories.firstOrNull()) }
    var expandedCategory by remember { mutableStateOf(false) }

    var description by remember { mutableStateOf("") }

    // Use LocalDate for createdAt
    var createdAt by remember { mutableStateOf(LocalDate.now()) }
    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }
    var showDatePicker by remember { mutableStateOf(false) }

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy", Locale.ENGLISH)
    val formattedDate = createdAt.format(dateFormatter)

    var isAmountValid by remember { mutableStateOf(false) } // Start with false since amountInput is empty


    val customColorScheme = lightColorScheme(
        primary = Color(0xFF7B1FA2),
        onPrimary = Color.White,
        secondary = Color(0xFF9C27B0),
        onSecondary = Color.White,
        surface = Color(0xFFF3E5F5),
        onSurface = Color.Black,
        background = Color(0xFFF3E5F5),
        onBackground = Color.Black
    )

    val customTypography = Typography().copy(
        titleLarge = Typography().titleLarge.copy(color = Color.Black, fontFamily = MontserratFontFamily),
        titleMedium = Typography().titleMedium.copy(color = Color.Black, fontFamily = MontserratFontFamily),
        titleSmall = Typography().titleSmall.copy(color = Color.Black, fontFamily = MontserratFontFamily),
        bodyLarge = Typography().bodyLarge.copy(color = Color.Black, fontFamily = MontserratFontFamily),
        bodyMedium = Typography().bodyMedium.copy(color = Color.Black, fontFamily = MontserratFontFamily),
        labelLarge = Typography().labelLarge.copy(color = Color.Black, fontFamily = MontserratFontFamily),
    )

    var isIncome by remember { mutableStateOf(true) }

    if (showDatePicker) {
        val pickerState = rememberDatePickerState(
            initialSelectedDateMillis = createdAt.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )

        MaterialTheme(
            colorScheme = customColorScheme,
            typography = customTypography
        ) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                            pickerState.selectedDateMillis?.let { millis ->
                                createdAt =
                                    Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                            }
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = pickerState)
            }
        }
    }

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = if (isIncome) Color(0xFFE91E63) else Color.Gray,
                        shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                    ),
                shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
                onClick = {
                    isIncome = true
                    selectedCategory = categories.firstOrNull()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isIncome) Color(0xFFE91E63) else Color.White.copy(alpha = 0.15f)
                ),
            ) {
                Text(
                    "Income",
                    color = Color.White
                )
            }
            Button(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = if (!isIncome) Color(0xFFE91E63) else Color.Gray,
                        shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                    ),
                shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
                onClick = {
                    isIncome = false
                    selectedCategory = categories.firstOrNull()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isIncome) Color(0xFFE91E63) else Color.White.copy(alpha = 0.15f)
                ),
            ) {
                Text(
                    "Expense",
                    color = Color.White
                )
            }
        }

        Spacer(Modifier.height(12.dp))

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
            onValueChange = { input ->
                val filtered = input.filter { c -> c.isDigit() || c == '.' }
                amountInput = filtered
                isAmountValid = filtered.toDoubleOrNull()?.let { it > 0.0 } ?: false
            },
            label = { Text("Amount", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = !isAmountValid && amountInput != "",
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = MontserratFontFamily,
                color = MaterialTheme.colorScheme.primary
            )
        )
        if (!isAmountValid && amountInput != "") {
            Text(
                text = "Please enter a valid amount greater than zero",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp, top = 4.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        // CATEGORY DROPDOWN
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

        Text(
            text = "Date: $formattedDate",
            color = Color.White,
            modifier = Modifier
                .clickable { showDatePicker = true }
                .padding(8.dp)
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = MontserratFontFamily,
                color = MaterialTheme.colorScheme.primary
            ),
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (selectedAccount != null && selectedCategory != null && isAmountValid && amountInput.isNotBlank()) {

                    Log.d("Is income :", isIncome.toString())
                    if (!isIncome) {
                        viewModel.addExpense(
                            accountId = selectedAccount!!.accountId,
                            amount = amountInput.toDoubleOrNull() ?: 0.0,
                            categoryId = selectedCategory!!.categoryId,
                            description = description,
                            createdAt = createdAt.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        )
                    } else {
                        viewModel.addIncome(
                            accountId = selectedAccount!!.accountId,
                            amount = amountInput.toDoubleOrNull() ?: 0.0,
                            categoryId = selectedCategory!!.categoryId,
                            description = description,
                            createdAt = createdAt.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        )
                    }

                    selectedAccount = accounts[0]
                    amountInput = ""
                    selectedCategory = categories[0]
                    description = ""
                    if (isIncome) coroutineScope.launch {
                        snackbarHostState.showSnackbar("Income added successfully")
                    }
                    if (!isIncome) coroutineScope.launch {
                        snackbarHostState.showSnackbar("Expense added successfully")
                    }
                    onCreated()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = isAmountValid && amountInput.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)
        ) {
            Text(text = "Add", color = Color.White)
        }
    }
}
