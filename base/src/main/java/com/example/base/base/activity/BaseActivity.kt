package com.example.base.base.activity

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseActivity<VDB : ViewDataBinding>(@LayoutRes val layoutRes: Int) :
    AppCompatActivity() {
    private val compositeDisposable= CompositeDisposable()

    lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.onCreate()
    }
    open fun VDB.onCreate() = Unit

    fun Disposable.addToDisposable(){
        compositeDisposable.add(this)
    }

    //토스트를 보여준다.
    protected fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}