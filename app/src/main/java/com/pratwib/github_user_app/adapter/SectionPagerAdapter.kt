package com.pratwib.github_user_app.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pratwib.github_user_app.datasource.UserResponse
import com.pratwib.github_user_app.ui.follows.FollowsFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var model: UserResponse? = null
    override fun getItemCount(): Int {
        return 2
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun createFragment(position: Int): Fragment {
        return FollowsFragment.newInstance(position + 1, model)
    }
}