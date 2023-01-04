package com.realize.android.domain.usecase

import com.realize.android.domain.repository.UserInfoRepository
import javax.inject.Inject

class SetUserLoginUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke(loginId:String?){
        if(loginId == null){
            return
        }

        //로그인 한  유저 알아보기위해  Id 저장하고 status를 저장한다.
        with(userInfoRepository){
            saveLoginId(loginId)
            saveLoginStatus(true)
        }
    }
}