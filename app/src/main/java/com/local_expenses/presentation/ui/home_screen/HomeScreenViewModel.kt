package com.local_expenses.presentation.ui.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.local_expenses.data.local.dao.AccountDao
import com.local_expenses.data.local.entity.AccountEntity
import com.local_expenses.data.local.entity.ExpenseEntity
import com.local_expenses.data.local.entity.IncomeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val accountDao: AccountDao
) : ViewModel() {

    lateinit var expenses : List<ExpenseEntity>;
    lateinit var incomes : List<IncomeEntity>;

    fun createAccount(account: AccountEntity){
        viewModelScope.launch {
            accountDao.insertAccount(
                account
            )
        }
    }

    fun getAllTransactions(){

    }
}