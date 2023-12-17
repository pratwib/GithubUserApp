package com.pratwib.github_user_app.networking

import com.pratwib.github_user_app.BuildConfig
import com.pratwib.github_user_app.datasource.SearchResponse
import com.pratwib.github_user_app.datasource.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    @Headers(
        "Authorization: token $API_TOKEN",
        "UserResponse-Agent: request"
    )
    suspend fun getListUsersAsync(): ArrayList<UserResponse>

    @GET("users/{username}")
    @Headers(
        "Authorization: token $API_TOKEN",
        "UserResponse-Agent: request"
    )
    suspend fun getDetailUserAsync(@Path("username") username: String): UserResponse

    @GET("search/users")
    @Headers(
        "Authorization: token $API_TOKEN",
        "UserResponse-Agent: request"
    )
    fun getUserBySearch(@Query("q") username: String): Call<SearchResponse>

    @GET("users/{username}/followers")
    @Headers(
        "Authorization: token $API_TOKEN",
        "UserResponse-Agent: request"
    )
    suspend fun getListFollowers(@Path("username") username: String): ArrayList<UserResponse>

    @GET("users/{username}/following")
    @Headers(
        "Authorization: token $API_TOKEN",
        "UserResponse-Agent: request"
    )
    suspend fun getListFollowing(@Path("username") username: String): ArrayList<UserResponse>

    companion object {
        private const val API_TOKEN = BuildConfig.API_TOKEN
    }
}
