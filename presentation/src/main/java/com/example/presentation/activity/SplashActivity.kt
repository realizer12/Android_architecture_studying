package com.example.presentation.activity

import android.content.Intent
import android.os.Looper
import android.os.Handler
import com.example.presentation.R
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivitySplashBinding
import com.example.local.PreferenceManager
import com.example.util.const.Const
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SplashActivity:BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun ActivitySplashBinding.onCreate() {
        checkUserAlreadyLogin()
    }

    private fun checkUserAlreadyLogin(){
        Observable.timer(2,TimeUnit.SECONDS)
            .map {
                PreferenceManager.getPreference(this@SplashActivity,
                    Const.PARAM_IS_LOGIN_SUCCESS,false) as Boolean
            }
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { isUserAlreadyLogin ->
                if(isUserAlreadyLogin){
                    goToMainScreen()
                }else{
                    goToLoginScreen()
                }
                finish()
            }.addToDisposable()
    }


    // 로그인 화면으로 가기
    private fun goToLoginScreen(){
        startActivity(Intent(this, LoginActivity::class.java))
    }

    //메인으로 가기
    private fun goToMainScreen(){
        startActivity(Intent(this, MainActivity::class.java))
    }
}