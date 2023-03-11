package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.base.viewmodel.BaseViewModel
import com.example.base.base.util.Event
import com.realize.android.domain.usecase.GetUserLoginStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserLoginStatusUseCase: GetUserLoginStatusUseCase
): BaseViewModel(){

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    init {
        checkUserAlreadyLogin()
    }

    private fun checkUserAlreadyLogin(){
       getUserLoginStatusUseCase().subscribe({
           _loginStatus.value = it
       },{
           _errorToast.value = Event(it)
       }).addDisposable()
    }

}