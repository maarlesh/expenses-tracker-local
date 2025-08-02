package com.local_expenses.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "income",
    foreignKeys = [
        ForeignKey(entity = CategoryEntity::class, parentColumns = ["categoryId"], childColumns = ["categoryId"]),
        ForeignKey(entity = AccountEntity::class, parentColumns = ["accountId"], childColumns = ["accountId"])
    ],
    indices = [Index("categoryId"), Index("accountId")]
)
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true) val incomeId: Int = 0,
    val categoryId: Int,
    val description: String,
    val amount: Double,
    val accountId: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),)
