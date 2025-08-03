package com.local_expenses.presentation.ui.creation_screen

import androidx.lifecycle.ViewModel
import com.local_expenses.data.local.dao.AccountDao
import com.local_expenses.data.local.dao.CategoryDao
import com.local_expenses.data.local.dao.ExpenseDao
import com.local_expenses.data.local.dao.IncomeDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CreationScreenViewModel @Inject constructor(
    private val accountDao: AccountDao,
    private val incomeDao: IncomeDao,
    private val expenseDao: ExpenseDao,
    private val categoryDao: CategoryDao,
) : ViewModel() {

}