package com.local_expenses.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "expense",
    foreignKeys = [
        ForeignKey(entity = CategoryEntity::class, parentColumns = ["categoryId"], childColumns = ["categoryId"]),
        ForeignKey(entity = AccountEntity::class, parentColumns = ["accountId"], childColumns = ["accountId"])
    ],
    indices = [Index("categoryId"), Index("accountId")]
)
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val expenseId: Int = 0,
    val categoryId: Int,
    val description: String,
    val amount: Double,
    val accountId: Int,
    val createdAt: Long
)
