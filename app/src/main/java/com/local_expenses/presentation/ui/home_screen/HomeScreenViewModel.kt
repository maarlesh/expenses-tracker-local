package com.local_expenses.presentation.ui.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.local_expenses.data.local.dao.AccountDao
import com.local_expenses.data.local.dao.CategoryDao
import com.local_expenses.data.local.dao.ExpenseDao
import com.local_expenses.data.local.dao.IncomeDao
import com.local_expenses.data.local.entity.AccountEntity
import com.local_expenses.data.local.entity.ExpenseEntity
import com.local_expenses.data.local.entity.IncomeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val accountDao: AccountDao,
    private val expenseDao : ExpenseDao,
    private val incomeDao : IncomeDao,
    private val categoryDao : CategoryDao,
) : ViewModel() {

    fun createAccount(account: AccountEntity){
        viewModelScope.launch {
            accountDao.insertAccount(
                account
            )
        }
    }

    private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expenses: StateFlow<List<ExpenseEntity>> = _expenses.asStateFlow()

    private val _incomes = MutableStateFlow<List<IncomeEntity>>(emptyList())
    val incomes: StateFlow<List<IncomeEntity>> = _incomes.asStateFlow()

    private val _categories = MutableStateFlow<Map<Int, String>>(emptyMap())
    val categories: StateFlow<Map<Int, String>> = _categories.asStateFlow()


    fun getAllTransactions(accounts: List<AccountEntity>, year: Int, month: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month-1)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val monthStartMillis = calendar.timeInMillis

        calendar.add(Calendar.MONTH, 1)
        val monthEndMillis = calendar.timeInMillis

        val accountIds = accounts.map { it.accountId }

        Log.d("Transaction : Account Ids" , accountIds.toString());
        Log.d("Transaction : MonthStar" , monthStartMillis.toString());
        Log.d("Transaction : MonthStar" , monthEndMillis.toString());

        viewModelScope.launch {
            val expenseList = expenseDao.getExpensesInMonth(accountIds, monthStartMillis, monthEndMillis)
            val incomeList = incomeDao.getIncomesInMonth(accountIds, monthStartMillis, monthEndMillis)


            _expenses.value = expenseList
            _incomes.value = incomeList

            Log.d("Transaction : expenses", _expenses.toString())
            Log.d("Transaction : incomes", _incomes.toString())
        }
    }

    fun loadCategoriesForUser(userId: Int) {
        viewModelScope.launch {
            categoryDao.getAllCategories(userId)
                .collect { categoryList ->
                    _categories.value = categoryList.associate { it.categoryId to it.name }
                }
        }
    }

}

// 1756665000000
// 1759257000000