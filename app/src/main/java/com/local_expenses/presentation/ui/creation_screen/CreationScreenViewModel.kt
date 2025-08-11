package com.local_expenses.presentation.ui.creation_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.local_expenses.data.local.dao.AccountDao
import com.local_expenses.data.local.dao.CategoryDao
import com.local_expenses.data.local.dao.ExpenseDao
import com.local_expenses.data.local.dao.IncomeDao
import com.local_expenses.data.local.entity.CategoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreationScreenViewModel @Inject constructor(
    private val accountDao: AccountDao,
    private val incomeDao: IncomeDao,
    private val expenseDao: ExpenseDao,
    private val categoryDao: CategoryDao,
) : ViewModel() {

    fun createCategory(categoryName: String, userId : Int){
        viewModelScope.launch {
            val category = CategoryEntity(
                name =  categoryName,
                userId = userId,
                icon = categoryName,

            )
            val id = categoryDao.insertCategory(category);

        }
    }
}