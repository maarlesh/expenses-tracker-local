package com.local_expenses.data.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.local_expenses.data.local.dao.AccountDao
import com.local_expenses.data.local.dao.CategoryDao
import com.local_expenses.data.local.dao.ExpenseDao
import com.local_expenses.data.local.dao.IncomeDao
import com.local_expenses.data.local.dao.TransferDao
import com.local_expenses.data.local.dao.UserDao
import com.local_expenses.data.local.entity.AccountEntity
import com.local_expenses.data.local.entity.CategoryEntity
import com.local_expenses.data.local.entity.ExpenseEntity
import com.local_expenses.data.local.entity.IncomeEntity
import com.local_expenses.data.local.entity.TransferEntity
import com.local_expenses.data.local.entity.UserEntity


@Database(
    entities = [
        ExpenseEntity::class,
        IncomeEntity::class,
        AccountEntity::class,
        CategoryEntity::class,
        UserEntity::class,
        TransferEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun incomeDao(): IncomeDao
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun userDao(): UserDao
    abstract fun transferDao(): TransferDao
}
