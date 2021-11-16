package com.dicoding.picodiploma.lastsubmission.data.response


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.lastsubmission.R
import com.dicoding.picodiploma.lastsubmission.databinding.FragmentFollowBinding
import com.dicoding.picodiploma.lastsubmission.ui.detail.FollowersViewModel
import com.dicoding.picodiploma.lastsubmission.ui.main.UserAdapter
import com.dicoding.picodiploma.lastsubmission.ui.detail.DetailUserActivity



class FollowersFragment:Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding?= null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }
            showLoading(true)
            viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
            viewModel.setListFollowers(username)
            viewModel.getListFollowers().observe(viewLifecycleOwner, {
                if (it != null) {
                    adapter.setList(it)
                    showLoading(false)
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun showLoading(state: Boolean) {
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

}