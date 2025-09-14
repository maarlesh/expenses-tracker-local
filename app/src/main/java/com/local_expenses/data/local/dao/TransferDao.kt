package com.local_expenses.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.local_expenses.data.local.entity.ExpenseEntity
import com.local_expenses.data.local.entity.TransferEntity

@Dao
interface TransferDao {

    @Insert()
    suspend fun insertTransfer(transfer : TransferEntity)

    @Query("""
    SELECT * FROM transfer
    WHERE createdAt >= :monthStartMillis
      AND createdAt < :monthEndMillis
""")
    suspend fun getTransfersInMonth(
        monthStartMillis: Long,
        monthEndMillis: Long
    ): List<TransferEntity>
}