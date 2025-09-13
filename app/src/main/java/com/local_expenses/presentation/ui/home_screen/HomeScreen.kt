package com.local_expenses.presentation.ui.home_screen
import android.os.Build
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.local_expenses.data.local.entity.ExpenseEntity
import com.local_expenses.data.local.entity.IncomeEntity
import com.local_expenses.presentation.theme.AppGradientBrush2
import com.local_expenses.presentation.theme.MontserratFontFamily
import com.local_expenses.presentation.ui.common.BottomNavBar
import kotlinx.coroutines.launch
import com.local_expenses.presentation.theme.PrimaryTextColor
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    accounts: List<AccountEntity>,
    userId : Int,
    onNavigateToCreation: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(YearMonth.now()) }

    LaunchedEffect(selectedDate, accounts) {
        viewModel.getAllTransactions(accounts, selectedDate.year, selectedDate.monthValue)
    }

    LaunchedEffect(userId) {
        viewModel.loadCategoriesForUser(userId)
    }

    val expenses by viewModel.expenses.collectAsStateWithLifecycle()
    val incomes by viewModel.incomes.collectAsStateWithLifecycle()

    val accountMap = remember(accounts) { accounts.associateBy { it.accountId } }

    val transactions = remember(expenses, incomes) {
        (expenses + incomes).sortedByDescending {
            (it as? ExpenseEntity)?.createdAt ?: (it as IncomeEntity).createdAt
        }
    }

    val groupedByDay = remember(expenses, incomes) {
        (expenses + incomes)
            .sortedByDescending {
                (it as? ExpenseEntity)?.createdAt ?: (it as IncomeEntity).createdAt
            }
            .groupBy {
                val millis = (it as? ExpenseEntity)?.createdAt ?: (it as IncomeEntity).createdAt
                SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH).format(Date(millis))
            }
    }

    val dailyTotals = remember(groupedByDay) {
        groupedByDay.mapValues { (_, transactions) ->
            val totalIncome = transactions.filterIsInstance<IncomeEntity>().sumOf { it.amount }
            val totalExpense = transactions.filterIsInstance<ExpenseEntity>().sumOf { it.amount }
            Pair(totalIncome, totalExpense)
        }
    }

    val categories by viewModel.categories.collectAsStateWithLifecycle()


    Log.d("Transactions : ", transactions.toString());

    fun formatDate(epochMillis: Long): String {
        val date = Date(epochMillis)
        val sdf = SimpleDateFormat("dd/MMM", Locale.ENGLISH)
        return sdf.format(date)
    }


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
                modifier = Modifier.height(8.dp)
            )
            if(transactions.isNotEmpty())
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 0.dp,
                        end = 0.dp,
                        top = 0.dp,
                        bottom = 90.dp,
                    ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                groupedByDay.forEach { (date, dailyTransactions) ->
                    val (totalIncome, totalExpense) = dailyTotals[date] ?: Pair(0.0, 0.0)
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = date,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text =  "-   ₹$totalExpense",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Red,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text =  "+   ₹$totalIncome",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Green,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }

                    items(dailyTransactions) { transaction ->
                        val amount = when (transaction) {
                            is IncomeEntity -> transaction.amount
                            is ExpenseEntity -> transaction.amount
                            else -> 0.0
                        }

                        TransactionCard(
                            isIncome = transaction is IncomeEntity,
                            date = formatDate(
                                (transaction as? ExpenseEntity)?.createdAt ?: (transaction as IncomeEntity).createdAt
                            ),
                            accountName = accountMap[  (transaction as? ExpenseEntity)?.accountId ?: (transaction as IncomeEntity).accountId]?.accountName ?: "Unknown",
                            amount = amount,
                            category = categories[(transaction as? ExpenseEntity)?.categoryId ?: (transaction as IncomeEntity).categoryId] ?: "",
                            description = (transaction as? ExpenseEntity)?.description ?: (transaction as IncomeEntity).description
                        )
                    }
                }
            }

            if(transactions.isEmpty()){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "No Data available",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomNavBar(
                onAddClicked = onNavigateToCreation
            )
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
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        items(accounts) { account ->
            AccountCard(account = account)
        }
        item {
            Box(
                modifier = Modifier
                    .height(100.dp)
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
