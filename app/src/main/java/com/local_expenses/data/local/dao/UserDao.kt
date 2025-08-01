package com.local_expenses.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.local_expenses.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity) : Long

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM user_details")
    fun getUserDetails(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user_details WHERE userId = :userId LIMIT 1")
    suspend fun getUserById(userId: Int): UserEntity?
}