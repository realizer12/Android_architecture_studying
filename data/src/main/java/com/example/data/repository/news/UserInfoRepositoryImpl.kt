package com.example.data.repository.news

import com.example.data.source.local.news.UserInfoLocalDataSource
import com.realize.android.domain.repository.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val userInfoLocalDataSource: UserInfoLocalDataSource
) : UserInfoRepository{
    override fun saveLoginStatus(status: Boolean) =
        userInfoLocalDataSource.saveLoginStatus(status)


    override fun saveLoginId(loginId: String) =
        userInfoLocalDataSource.saveLoginId(loginId)


    override fun getLoginStatus(): Boolean {
       return userInfoLocalDataSource.getLoginStatus()
    }

    override fun removeAllSharedPreference() {
        userInfoLocalDataSource.removeAllSharedPreference()
    }
}