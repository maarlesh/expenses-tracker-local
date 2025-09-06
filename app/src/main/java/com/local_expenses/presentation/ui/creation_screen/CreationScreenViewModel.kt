package com.local_expenses.presentation.ui.creation_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.local_expenses.data.local.dao.AccountDao
import com.local_expenses.data.local.dao.CategoryDao
import com.local_expenses.data.local.dao.ExpenseDao
import com.local_expenses.data.local.dao.IncomeDao
import com.local_expenses.data.local.entity.CategoryEntity
import com.local_expenses.data.local.entity.ExpenseEntity
import com.local_expenses.data.local.entity.IncomeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.stateIn
import java.sql.Time


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
        createdAt: Long
    ){
        viewModelScope.launch {
            val income = IncomeEntity(
                categoryId = categoryId,
                description = description,
                amount = amount,
                accountId = accountId,
                createdAt = createdAt
            )
            incomeDao.addIncomeAndUpdateBalance(income)
        }
    }

    fun addExpense(
        categoryId: Int,
        description : String,
        amount : Double,
        accountId : Int,
        createdAt : Long
    ){
        viewModelScope.launch {
            val expense = ExpenseEntity(
                categoryId = categoryId,
                description = description,
                amount = amount,
                accountId = accountId,
                createdAt = createdAt
            )
            expenseDao.addExpenseAndUpdateBalance(expense)
        }
    }

    fun transferAmount(
        accountIdFrom : Int,
        accountIdTo : Int,
        amount : Double,
    ){
        viewModelScope.launch {
            var accountFrom = accountDao.getAccountById(accountIdFrom);
            var accountTo = accountDao.getAccountById(accountIdTo);
            if(accountTo?.balance != null && accountFrom?.balance != null){
                Log.d("Account: Entered here" , amount.toString())
                accountFrom.balance -= amount;
                accountTo.balance += amount;
                accountFrom.updatedAt = System.currentTimeMillis();
                accountTo.updatedAt = System.currentTimeMillis();
            }
            Log.d("Account : ", accountFrom.toString());
            Log.d("Account To :", accountTo.toString());
            accountDao.updateAccount(accountFrom);
            accountDao.updateAccount(accountTo);
            Log.d("Account : ", accountFrom.toString());
            Log.d("Account To :", accountTo.toString());
        }
    }

    val accounts = accountDao.getAccountsByUser(userId)
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val categories = categoryDao.getAllCategories(userId)
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}