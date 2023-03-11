package com.example.presentation.activity

import android.content.Intent
import androidx.activity.viewModels
import com.example.base.base.BaseActivity
import com.example.presentation.R
import com.example.presentation.databinding.ActivitySplashBinding
import com.example.base.base.SingleEventObserver
import com.example.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity:  BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val splashViewModel:SplashViewModel by viewModels()

    override fun ActivitySplashBinding.onCreate() {
        getDataFromVm()
    }

    // 로그인 화면으로 가기
    private fun goToLoginScreen(){
        startActivity(Intent(this, LoginActivity::class.java))
    }

    //메인으로 가기
    private fun goToMainScreen(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun getDataFromVm(){
        splashViewModel.errorToast.observe(this, SingleEventObserver{
            showToast(it.message.toString())
        })

        splashViewModel.loginStatus.observe(this) { isUserAlreadyLogin ->
            if(isUserAlreadyLogin){
                goToMainScreen()
            }else{
                goToLoginScreen()
            }
            finish()
        }

    }



}