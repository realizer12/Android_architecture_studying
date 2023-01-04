package com.example.data.source.local.news

/**
 * 유저 정보 로컬 데이터 소스
**/
interface UserInfoLocalDataSource {
    fun saveLoginStatus(status:Boolean)//로그인 여부를  저장함.
    fun saveLoginId(loginId:String)//로그인 id를 저장함.
    fun getLoginStatus():Boolean//로그인 여부
    fun removeAllSharedPreference()
}