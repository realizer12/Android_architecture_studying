package com.realize.android.domain.repository

interface UserInfoRepository {
    fun saveLoginStatus(status:Boolean)//로그인 여부를  저장함.
    fun saveLoginId(loginId:String)//로그인 id를 저장함.
    fun getLoginStatus():Boolean//로그인 여부
    fun removeAllSharedPreference()
}