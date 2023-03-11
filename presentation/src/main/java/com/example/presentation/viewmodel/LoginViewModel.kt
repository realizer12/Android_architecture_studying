package com.example.presentation.viewmodel

import com.example.base.base.viewmodel.BaseViewModel
import com.realize.android.domain.usecase.SetUserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * 로그인 화면용 뷰모델 이다. 
**/
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val setUserLoginUseCase: SetUserLoginUseCase
): BaseViewModel() {

    fun setUserLogin(loginUserId:String?){
        setUserLoginUseCase(loginId = loginUserId)
    }
}