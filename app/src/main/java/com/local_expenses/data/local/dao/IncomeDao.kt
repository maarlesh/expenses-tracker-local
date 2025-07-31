package com.local_expenses.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.local_expenses.data.local.entity.IncomeEntity

@Dao
interface IncomeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(income: IncomeEntity)

    @Delete
    suspend fun deleteIncome(income : IncomeEntity)

    @Query("SELECT * FROM income")
    suspend fun getAllExpenses(): List<IncomeEntity>

    @Query("UPDATE account SET balance = balance + :amount WHERE accountId = :accountId")
    suspend fun increaseAccountBalance(accountId: Int, amount: Double)

    @Transaction
    suspend fun addIncomeAndUpdateBalance(income: IncomeEntity) {
        insertIncome(income)
        increaseAccountBalance(income.accountId, income.amount)
    }

}
