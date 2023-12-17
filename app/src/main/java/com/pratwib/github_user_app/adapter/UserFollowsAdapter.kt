package com.pratwib.github_user_app.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pratwib.github_user_app.databinding.ItemUserFollowsBinding
import com.pratwib.github_user_app.datasource.UserResponse

class UserFollowsAdapter :
    RecyclerView.Adapter<UserFollowsAdapter.MyViewHolder>() {
    private var listUserResponse = ArrayList<UserResponse>()

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(items: ArrayList<UserResponse>) {
        listUserResponse.clear()
        listUserResponse.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            ItemUserFollowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listUserResponse[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUserResponse[position]) }

    }

    override fun getItemCount() = listUserResponse.size

    class MyViewHolder(private var binding: ItemUserFollowsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userResponse: UserResponse) {
            binding.tvItemName.text = userResponse.login
            Glide.with(binding.root)
                .load(userResponse.avatarUrl)
                .circleCrop()
                .into(binding.imgItemPhoto)
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}
