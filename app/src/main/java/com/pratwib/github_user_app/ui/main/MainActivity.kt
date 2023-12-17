package com.pratwib.github_user_app.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.pratwib.github_user_app.adapter.OnItemClickCallback
import com.pratwib.github_user_app.adapter.UserAdapter
import com.pratwib.github_user_app.databinding.ActivityMainBinding
import com.pratwib.github_user_app.datasource.UserResponse
import com.pratwib.github_user_app.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter: UserAdapter by lazy {
        UserAdapter()
    }

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpSearchView()
        observeAnimationAndProgressBar()

        mainViewModel.user.observe(this) { userResponse ->
            if (userResponse != null) {
                adapter.addDataToList(userResponse)
                setUserData()
            }
        }
    }

    private fun setUpSearchView() {
        with(binding) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    showProgressBar(true)
                    mainViewModel.getUserBySearch(query)
                    mainViewModel.searchUser.observe(this@MainActivity) { searchUserResponse ->
                        if (searchUserResponse != null) {
                            adapter.addDataToList(searchUserResponse)
                            setUserData()
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
    }

    private fun observeAnimationAndProgressBar() {
        mainViewModel.isLoading.observe(this) {
            showProgressBar(it)
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun setUserData() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvMain.layoutManager = layoutManager
        binding.rvMain.adapter = adapter
        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(user: UserResponse) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.KEY_USER, user)
                startActivity(intent)
            }
        })
    }
}
