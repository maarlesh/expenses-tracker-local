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
import com.local_expenses.presentation.theme.AppGradientBrush2
import com.local_expenses.presentation.theme.MontserratFontFamily
import kotlinx.coroutines.launch

@Composable
fun CreateTransfer(viewModel: CreationScreenViewModel, accounts : List<AccountEntity>,){
    var selectedAccountFrom by remember { mutableStateOf(accounts.firstOrNull()) }
    var selectedAccountTo by remember {mutableStateOf(accounts[1])}

    var expandedAccountFrom by remember { mutableStateOf(false) }
    var expandedAccountTo by remember { mutableStateOf(false) }


    var amountInput by remember { mutableStateOf("") }

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
                .clickable { expandedAccountFrom = true }
        ) {
            OutlinedTextField(
                value = selectedAccountFrom?.accountName ?: "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                label = { Text("Select Account From", color = Color.White) },
                trailingIcon = {
                    IconButton(onClick = { expandedAccountFrom = !expandedAccountFrom }) {
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
                expanded = expandedAccountFrom,
                onDismissRequest = { expandedAccountFrom = false },
                modifier = Modifier.background(AppGradientBrush2)
            ) {
                accounts.forEach { account ->
                    DropdownMenuItem(
                        text = { Text(account.accountName) },
                        onClick = {
                            selectedAccountFrom = account
                            expandedAccountFrom = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedAccountTo = true }
        ) {
            OutlinedTextField(
                value = selectedAccountTo?.accountName ?: "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                label = { Text("Select Account To", color = Color.White) },
                trailingIcon = {
                    IconButton(onClick = { expandedAccountTo = !expandedAccountTo }) {
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
                expanded = expandedAccountTo,
                onDismissRequest = { expandedAccountTo = false },
                modifier = Modifier.background(AppGradientBrush2)
            ) {
                accounts.forEach { account ->
                    DropdownMenuItem(
                        text = { Text(account.accountName) },
                        onClick = {
                            selectedAccountTo = account
                            expandedAccountTo = false
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
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = MontserratFontFamily,
                color = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(Modifier.height(12.dp))

        Text(text = "Category", color = Color.White, style = MaterialTheme.typography.bodyMedium)

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (selectedAccountFrom != null) {
                    viewModel.transferAmount(
                        accountIdFrom = selectedAccountFrom!!.accountId,
                        accountIdTo = selectedAccountTo!!.accountId,
                        amount = amountInput.toDoubleOrNull() ?: 0.0,
                    )
                    selectedAccountFrom = accounts[0];
                    selectedAccountTo = accounts[1];
                    amountInput = "";
                    description = "";
                    scope.launch {
                        snackbarHostState.showSnackbar("Expense added successfully")
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