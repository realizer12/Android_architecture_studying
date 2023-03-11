package com.example.presentation.activity

import android.content.Intent
import androidx.activity.viewModels
import com.example.presentation.R
import com.example.base.base.BaseActivity
import com.example.util.const.Const.PARAM_IS_LOGIN_ID
import com.example.util.const.Const.PARAM_IS_LOGIN_SUCCESS
import com.example.presentation.databinding.ActivityLoginBinding
import com.example.presentation.enum.MockUser
import com.example.local.PreferenceManager
import com.example.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    //로그인 화면 뷰모델
    private val loginViewModel:LoginViewModel by viewModels()

    override fun ActivityLoginBinding.onCreate() {
        binding.loginActivity = this@LoginActivity
        binding.lifecycleOwner = this@LoginActivity
    }

    fun login(){
        if(isLoginInfoValid()){//로그인 정보가 모두 맞는 경우
            goToMainScreen()
            finish()
        }else{
            showToast("로그인 정보가 틀렸습니다.")
        }
    }

    //로그인 정보 유효한지 체크
    private fun isLoginInfoValid():Boolean{
        val writtenId = binding.editLoginId.text.toString()
        val writtenPwd = binding.editLoginPwd.text.toString()
        return MockUser.values().any { it.userID == writtenId && it.password == writtenPwd }
    }

    //메인으로 가기
    private fun goToMainScreen(){

        //로그인 성공했다는 정보 저장
        loginViewModel.setUserLogin(loginUserId = binding.editLoginId.text.toString())
        startActivity(Intent(this, MainActivity::class.java))
    }
}