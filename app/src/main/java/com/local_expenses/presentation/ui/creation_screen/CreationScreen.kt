package com.local_expenses.presentation.ui.creation_screen

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.local_expenses.presentation.theme.AppGradientBrush2
import com.local_expenses.presentation.ui.common.BottomNavBar


enum class CreationTab {
    Expense, Transfer, Category
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreationScreen(
    navController: NavController,
    viewModel: CreationScreenViewModel,
    userId : Int,
    onHomeTapped: () -> Unit,
    ) {
    var selectedTab by remember { mutableStateOf(CreationTab.Expense) }
    val accounts by viewModel.accounts.collectAsState()
    val categories by viewModel.categories.collectAsState()


    val tabItems = listOf(CreationTab.Expense, CreationTab.Transfer, CreationTab.Category)
    val filteredTabs = if (accounts.count() > 1) tabItems else tabItems.filter { it != CreationTab.Transfer }




    Box(
        modifier = Modifier
            .background(AppGradientBrush2)
            .systemBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize(),
        ) {

            Spacer(Modifier.height(28.dp))
            // Tab Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                filteredTabs.forEachIndexed { index, tab ->
                    CreationTabItem(
                        label = tab.name,
                        isSelected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        index = index,
                        totalCount = filteredTabs.size
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                CreationTab.Expense -> CreateExpense(viewModel,accounts, categories, onCreated(navController, userId))
                CreationTab.Transfer -> CreateTransfer(viewModel, accounts, onCreated(navController, userId))
                CreationTab.Category -> CreateCategory(viewModel, userId, onCreated(navController, userId))
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                BottomNavBar(
                    onHomeClicked = onHomeTapped
                )
            }
        }
    }
}

fun onCreated(navController: NavController, userId: Int): () -> Unit = {
    navController.navigate("home/$userId") {
        popUpTo("create_transaction/$userId") { inclusive = true }
    }
}

@Composable
fun CreationTabItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    index: Int,
    totalCount: Int
) {
    val backgroundColor = if (isSelected)
        Color.White.copy(alpha = 0.6f)
    else
        Color.White.copy(alpha = 0.25f)

    val borderColor = if (isSelected)
        MaterialTheme.colorScheme.primary
    else
        Color.White.copy(alpha = 0.12f)

    val shape = when (index) {
        0 -> RoundedCornerShape(topStart = 28.dp, bottomStart = 28.dp, topEnd = 0.dp, bottomEnd = 0.dp)
        totalCount -1 -> RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 28.dp, bottomEnd = 28.dp)
        else -> RoundedCornerShape(0.dp)
    }

    Box(
        modifier = Modifier
            .background(backgroundColor, shape)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(borderColor, borderColor)
                ),
                shape = shape
            )
            .clickable { onClick() }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
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
