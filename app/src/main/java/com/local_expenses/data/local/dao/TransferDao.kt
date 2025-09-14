package com.local_expenses.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.local_expenses.data.local.entity.TransferEntity

@Dao
interface TransferDao {

    @Insert()
    suspend fun insertTransfer(transfer : TransferEntity)
}