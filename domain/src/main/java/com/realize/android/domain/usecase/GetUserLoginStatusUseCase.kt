package com.realize.android.domain.usecase

import com.realize.android.domain.repository.UserInfoRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetUserLoginStatusUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke(): Observable<Boolean> {
        return Observable.timer(2, TimeUnit.SECONDS)
            .map {
                userInfoRepository.getLoginStatus()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}