package com.local_expenses.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transfer",
    foreignKeys = [
        ForeignKey(entity = AccountEntity::class, parentColumns = ["accountId"], childColumns = ["fromAccountId"]),
        ForeignKey(entity = AccountEntity::class, parentColumns = ["accountId"], childColumns = ["toAccountId"])
    ],
    indices = [Index("transferId"), Index("fromAccountId"), Index("toAccountId")]
)
data class TransferEntity(
    @PrimaryKey(autoGenerate = true) val transferId: Int = 0,
    val description: String,
    val amount: Double,
    val fromAccountId: Int,
    val toAccountId: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),)
