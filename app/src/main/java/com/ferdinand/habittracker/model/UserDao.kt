package com.ferdinand.habittracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    fun selectUser(username: String, password: String): User?

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    fun findByUsername(username: String): User?
}