package com.local_expenses.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_details")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val name: String,
    val password: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)