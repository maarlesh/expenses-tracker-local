package com.local_expenses.presentation.ui.creation_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.local_expenses.data.local.dao.AccountDao
import com.local_expenses.data.local.dao.CategoryDao
import com.local_expenses.data.local.dao.ExpenseDao
import com.local_expenses.data.local.dao.IncomeDao
import com.local_expenses.data.local.entity.CategoryEntity
import com.local_expenses.data.local.entity.IncomeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.stateIn


@HiltViewModel
class CreationScreenViewModel @Inject constructor(
    private val accountDao: AccountDao,
    private val incomeDao: IncomeDao,
    private val expenseDao: ExpenseDao,
    private val categoryDao: CategoryDao,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private val userId: Int = checkNotNull(savedStateHandle.get<String>("userId")).toInt();

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

    fun addIncome(
        categoryId : Int,
        description : String,
        amount : Double,
        accountId : Int,
    ){
        viewModelScope.launch {
            val income = IncomeEntity(
                categoryId = categoryId,
                description = description,
                amount = amount,
                accountId = accountId
            )
            incomeDao.addIncomeAndUpdateBalance(income)
        }
    }

    val accounts = accountDao.getAccountsByUser(userId)
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val categories = categoryDao.getAllCategories(userId)
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}