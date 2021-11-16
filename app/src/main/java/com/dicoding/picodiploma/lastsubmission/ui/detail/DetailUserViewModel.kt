package com.dicoding.picodiploma.lastsubmission.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.lastsubmission.api.RetrofitClient
import com.dicoding.picodiploma.lastsubmission.data.lokal.LockedUser
import com.dicoding.picodiploma.lastsubmission.data.lokal.LockedUserDao
import com.dicoding.picodiploma.lastsubmission.data.lokal.UserDatabase
import com.dicoding.picodiploma.lastsubmission.data.response.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: LockedUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.LockedUserDao()
    }

    val user = MutableLiveData<DetailUserResponse>()
    fun setUserNameDetail(username: String) {
        RetrofitClient.apiInstance
            .getDetailUsers(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>,
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToLocked(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = LockedUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToLocked(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromLockedUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromLocked(id)
        }
    }

}