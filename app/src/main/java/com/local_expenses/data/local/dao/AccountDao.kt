package com.local_expenses.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.local_expenses.data.local.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountEntity)

    @Update
    suspend fun updateAccount(account: AccountEntity?)

    @Delete
    suspend fun deleteAccount(account: AccountEntity)

    @Query("SELECT * FROM account")
    suspend fun getAllAccounts(): List<AccountEntity>

    @Query("SELECT * FROM account WHERE accountId = :id")
    suspend fun getAccountById(id: Int): AccountEntity?

    @Query("SELECT * FROM account WHERE userId = :userId")
    fun getAccountsByUser(userId: Int): Flow<List<AccountEntity>>
}
