package com.local_expenses.presentation.ui.home_screen
import android.R
import android.os.Build
import android.util.Log
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.local_expenses.data.local.entity.AccountEntity
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.local_expenses.presentation.theme.AppGradientBrush2
import com.local_expenses.presentation.theme.MontserratFontFamily
import com.local_expenses.presentation.ui.common.BottomNavBar
import kotlinx.coroutines.launch
import com.local_expenses.presentation.theme.PrimaryTextColor
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    accounts: List<AccountEntity>,
    userId : Int
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(YearMonth.of(2025, 8)) }

    if(showBottomSheet){
        ModalBottomSheet(onDismissRequest = {
            scope.launch { sheetState.hide() }
            showBottomSheet = false },
             containerColor = Color.Transparent
            ) {
            AddAccountSheetContent(
                onAddAccount = { name, balance ->
                    viewModel.createAccount(AccountEntity(
                        accountName = name,
                        balance = balance,
                        userId = userId
                    ))
                    showBottomSheet = false
                    scope.launch { sheetState.hide() }
                },
                onCancelPressed = {
                    showBottomSheet = false
                }
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppGradientBrush2)
            .systemBarsPadding()
    ) {
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
        ) {
            Text(
                "Accounts",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            AccountCarousel(accounts, onAddClicked = {
                showBottomSheet = true
            })
            Text(
                "Transactions  ",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MonthSwitcher(
                    selectedDate = selectedDate,
                    onMonthChange = { selectedDate = it }
                )
            }
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 0.dp,
                        end = 0.dp,
                        top = 0.dp,
                        bottom = 90.dp,
                    ),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ){
                item {
                    TransactionCard(
                        isIncome = true,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }
                item {
                    TransactionCard(
                        isIncome = false,
                        date = "01/08/2025",
                        accountName = "KVB",
                        amount = 1000,
                        category = "🍴",
                        description = "Dinner at KFC"
                    )
                }

            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomNavBar()
        }
    }
}



@Composable
fun AccountCarousel(
    accounts: List<AccountEntity>,
    modifier: Modifier = Modifier,
    onAddClicked: () -> Unit
) {
    Log.d("Accounts for the user", accounts.toString())
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        items(accounts) { account ->
            AccountCard(account = account)
        }
        item {
            Box(
                modifier = Modifier
                    .height(112.dp)
                    .background(Color.White.copy(alpha = 0.25f), RoundedCornerShape(28.dp))
                    .border(
                        1.dp,
                        Brush.linearGradient(
                            listOf(Color.White.copy(alpha = 0.55f), Color.White.copy(alpha = 0.12f))
                        ),
                        RoundedCornerShape(28.dp)
                    )
                    .clickable {
                        onAddClicked()
                    }
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(44.dp)
                            .shadow(10.dp, shape = CircleShape)
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Spacer(
                        modifier = Modifier.width(
                            12.dp
                        )
                    )
                    Text(
                        text = "Add an account",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}


@Composable
fun AddAccountSheetContent(
    onAddAccount: (String, Double) -> Unit,
    onCancelPressed : () -> Unit
) {
    var accountName by remember { mutableStateOf("") }
    var balanceInput by remember { mutableStateOf("") }
    val isValidBalance = balanceInput.toDoubleOrNull() != null && balanceInput.toDouble() >= 0.0

    Column(
        Modifier
            .background(Color.White, RoundedCornerShape(28.dp))
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp, top = 24.dp)
    ) {
        Text(
            text = "Add New Account",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = accountName,
            onValueChange = { accountName = it },
            label = { Text("Account Name", color = Color.Black, fontFamily = MontserratFontFamily) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Text),
            textStyle =TextStyle(
                color = Color.Black,
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFF69B4),
                unfocusedBorderColor = Color.Gray,
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = balanceInput,
            onValueChange = { balanceInput = it },
            label = { Text("Initial Balance", color = Color.Black, fontFamily = MontserratFontFamily) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
            singleLine = true,
            textStyle =TextStyle(
                color = Color.Black,
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFF69B4),
                unfocusedBorderColor = Color.Gray,
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = ({
                    onCancelPressed()
                    accountName = ""
                    balanceInput = ""
                }),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
            }
            Button(
                onClick = {
                    onAddAccount(accountName.trim(), balanceInput.toDouble())
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                enabled = accountName.isNotBlank() && isValidBalance,
                modifier = Modifier.weight(1f)
            ) {
                Text("Add", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthSwitcher(
    selectedDate: YearMonth,
    onMonthChange: (YearMonth) -> Unit
) {
    val now = YearMonth.now()
    Box(modifier = Modifier
        .background(Color.White.copy(alpha = 0.25f), RoundedCornerShape(28.dp))
        .border(
            1.dp,
            Brush.linearGradient(
                listOf(Color.White.copy(alpha = 0.55f), Color.White.copy(alpha = 0.12f))
            ),
            RoundedCornerShape(28.dp)
        )
        )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            IconButton(
                onClick = { onMonthChange(selectedDate.minusMonths(1)) }
            ) {
                Icon(
                    Icons.Default.ChevronLeft,
                    contentDescription = "Previous Month",
                    tint = PrimaryTextColor
                )
            }
            Text(
                text = "${
                    selectedDate.month.name.lowercase().replaceFirstChar { it.uppercase() }
                } ${selectedDate.year}",
                style = MaterialTheme.typography.titleSmall,
                color = PrimaryTextColor,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            IconButton(
                onClick = { onMonthChange(selectedDate.plusMonths(1)) },
                enabled = selectedDate < now
            ) {
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = "Next Month",
                    tint = PrimaryTextColor
                )
            }
        }
    }
}
