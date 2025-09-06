package com.local_expenses.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.local_expenses.data.local.entity.ExpenseEntity

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("UPDATE account SET balance = balance - :amount WHERE accountId = :accountId")
    suspend fun reduceAccountBalance(accountId: Int, amount: Double)

    @Query("SELECT * FROM expense")
    suspend fun getAllExpenses(): List<ExpenseEntity>

    @Query("""
    SELECT * FROM expense
    WHERE accountId in (:accountIds)
      AND createdAt >= :monthStartMillis
      AND createdAt < :monthEndMillis
""")
    suspend fun getExpensesInMonth(
        accountIds: List<Int>,
        monthStartMillis: Long,
        monthEndMillis: Long
    ): List<ExpenseEntity>


    @Transaction
    suspend fun addExpenseAndUpdateBalance(expense: ExpenseEntity) {
        insertExpense(expense)
        reduceAccountBalance(expense.accountId, expense.amount)
    }
}
