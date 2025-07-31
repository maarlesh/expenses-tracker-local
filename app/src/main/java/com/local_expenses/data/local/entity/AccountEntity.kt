package com.local_expenses.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "account",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("userId")]
)
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val accountId: Int = 0,
    val accountName: String,
    val balance: Double,
    val userId: Int,
    val createdAt: Long,
    val updatedAt: Long,
)
