package com.dicoding.picodiploma.lastsubmission.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.lastsubmission.data.lokal.LockedUser
import com.dicoding.picodiploma.lastsubmission.data.lokal.LockedUserDao
import com.dicoding.picodiploma.lastsubmission.data.lokal.UserDatabase

class LockedViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: LockedUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.LockedUserDao()
    }

    fun getFavortiteUser(): LiveData<List<LockedUser>>? {
        return userDao?.getLockedUser()
    }

}