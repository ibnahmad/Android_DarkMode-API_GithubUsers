package com.dicoding.picodiploma.lastsubmission.ui.main


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.picodiploma.lastsubmission.data.response.User
import com.dicoding.picodiploma.lastsubmission.databinding.ItemUserBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list = ArrayList<User>()
    var onItemClickCallback: OnItemClickCallback?= null


    @JvmName("setOnItemClickCallback1")
    fun setOnItemClickCallback (onItemClickCallBack: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallBack
    }

    fun setList(users: ArrayList<User>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClick(user)
            }
            binding.apply {
                tvUsername.text= user.login
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivUser)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }
    override fun getItemCount(): Int =list.size
    interface OnItemClickCallback{
        fun onItemClick(data: User)
    }
}