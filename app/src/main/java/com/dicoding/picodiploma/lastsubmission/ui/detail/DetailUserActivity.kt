package com.dicoding.picodiploma.lastsubmission.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.picodiploma.lastsubmission.databinding.ActivityDetailUserBinding
import com.dicoding.picodiploma.lastsubmission.ui.detail.DetailUserViewModel
import com.dicoding.picodiploma.lastsubmission.ui.detail.SectionPagerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        if (username != null) {
            viewModel.setUserNameDetail(username)
        }
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    tvUsernameDetail.text = it.login
                    tvFullNameDetail.text = it.name
                    tvLocation.text = StringBuilder("Nation: ${it.location}")
                    tvRepositoryDetail.text = StringBuilder("${it.public_repos} Repository")
                    tvFollowers.text = StringBuilder("${it.followers} Followers")
                    tvFollowing.text = StringBuilder("${it.following} Following")
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleLocked.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleLocked.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }
        binding.toggleLocked.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.  addToLocked(username.toString(), id, avatarUrl.toString())
            } else {
                viewModel.removeFromLockedUser(id)
            }
            binding.toggleLocked.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }
}