package com.example.local.feature.news.impl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.data.source.local.news.UserInfoLocalDataSource
import com.example.util.const.Const
import javax.inject.Inject

class UserInfoLocalDataSourceImpl @Inject constructor(
    private val preferences: SharedPreferences
):UserInfoLocalDataSource {
    override fun saveLoginStatus(status: Boolean) {
        preferences.edit {
            putBoolean(Const.PARAM_IS_LOGIN_SUCCESS,status)
        }
    }

    override fun saveLoginId(loginId: String) {
        preferences.edit {
            putString(Const.PARAM_IS_LOGIN_ID,loginId)
        }
    }

    override fun getLoginStatus(): Boolean {
        return try {
            preferences.getBoolean(Const.PARAM_IS_LOGIN_SUCCESS, false)
        } catch (e: Exception) {
            false
        }
    }

    override fun removeAllSharedPreference() {
        preferences.edit {
              this.clear()
        }
    }

}