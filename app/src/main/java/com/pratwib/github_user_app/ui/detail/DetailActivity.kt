package com.pratwib.github_user_app.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.pratwib.github_user_app.R
import com.pratwib.github_user_app.adapter.SectionPagerAdapter
import com.pratwib.github_user_app.databinding.ActivityDetailBinding
import com.pratwib.github_user_app.datasource.UserResponse

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.detailDataLayout.visibility = View.GONE
        val user = intent.getParcelableExtra<UserResponse>(KEY_USER)
        user?.login?.let {
            setViewModel(it)
        }
    }

    private fun setViewModel(username: String) {
        val detailViewModel: DetailViewModel by viewModels {
            DetailViewModelFactory(username)
        }

        detailViewModel.isLoading.observe(this) {
            showProgressBar(it)
        }

        detailViewModel.detailUser.observe(this@DetailActivity) { userResponse ->
            if (userResponse != null) {
                setData(userResponse)
                setTabLayoutAdapter(userResponse)
            }
        }
    }

    private fun setTabLayoutAdapter(user: UserResponse) {
        val sectionPagerAdapter = SectionPagerAdapter(this@DetailActivity)
        sectionPagerAdapter.model = user
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setData(userResponse: UserResponse?) {
        if (userResponse != null) {
            with(binding) {
                detailDataLayout.visibility = View.VISIBLE
                detailImage.visibility = View.VISIBLE
                Glide.with(root)
                    .load(userResponse.avatarUrl)
                    .circleCrop()
                    .into(binding.detailImage)
                detailName.visibility = View.VISIBLE
                detailUsername.visibility = View.VISIBLE
                detailName.text = userResponse.name
                detailUsername.text = userResponse.login
                detailCompany.visibility = View.VISIBLE
                detailCompany.text = userResponse.company
                detailLocation.visibility = View.VISIBLE
                detailLocation.text = userResponse.location
                detailFollowersValue.visibility = View.VISIBLE
                detailFollowersValue.text = userResponse.followers.toString()
                detailFollowers.visibility = View.VISIBLE
                detailFollowingValue.visibility = View.VISIBLE
                detailFollowingValue.text = userResponse.following.toString()
                detailFollowing.visibility = View.VISIBLE
                detailRepoValue.visibility = View.VISIBLE
                detailRepoValue.text = userResponse.publicRepos.toString()
                detailRepo.visibility = View.VISIBLE
            }
        } else {
            Log.i(TAG, "setData fun is error")
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val KEY_USER = "user"
        private const val TAG = "DetailActivity"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}