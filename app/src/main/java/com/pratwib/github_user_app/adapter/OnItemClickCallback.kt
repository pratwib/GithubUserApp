package com.pratwib.github_user_app.adapter

import com.pratwib.github_user_app.datasource.UserResponse

interface OnItemClickCallback {
    fun onItemClicked(user: UserResponse)
}