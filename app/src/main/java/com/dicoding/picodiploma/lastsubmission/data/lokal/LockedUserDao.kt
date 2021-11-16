package com.dicoding.picodiploma.lastsubmission.data.lokal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LockedUserDao {

    @Insert
    suspend fun addToLocked(lockedUser: LockedUser)

    @Query("SELECT * FROM favorite_user")
    fun getLockedUser(): LiveData<List<LockedUser>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id= :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id= :id")
    suspend fun removeFromLocked(id: Int): Int

}