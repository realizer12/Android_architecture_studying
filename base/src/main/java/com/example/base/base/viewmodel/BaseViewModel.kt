package com.example.base.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.base.base.util.Event
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.addTo

/**
 * 베이스가 되는 뷰모델이다. 
**/
abstract class BaseViewModel:ViewModel(){
    private val compositeDisposable = CompositeDisposable()

    //error toast는 공통 사용이므로, base viewmodel 에 넣어줌.
    protected val _errorToast = MutableLiveData<Event<Throwable>>()
    val errorToast: LiveData<Event<Throwable>> = _errorToast

    fun Disposable.addDisposable(){
        this.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

}