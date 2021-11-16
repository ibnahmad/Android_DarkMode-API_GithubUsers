package com.dicoding.picodiploma.lastsubmission.ui.favorite


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.lastsubmission.data.lokal.LockedUser
import com.dicoding.picodiploma.lastsubmission.data.response.User
import com.dicoding.picodiploma.lastsubmission.databinding.ActivityFavoriteBinding
import com.dicoding.picodiploma.lastsubmission.ui.main.UserAdapter
import com.dicoding.picodiploma.lastsubmission.ui.detail.DetailUserActivity

class LockedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: LockedViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(LockedViewModel::class.java)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClick(data: User) {
                Intent(this@LockedActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@LockedActivity)
            rvUser.adapter = adapter
        }

        viewModel.getFavortiteUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        })

    }

    private fun mapList(users: List<LockedUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url,
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}