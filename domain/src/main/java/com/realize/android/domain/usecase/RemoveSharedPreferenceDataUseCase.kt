package com.realize.android.domain.usecase

import com.realize.android.domain.repository.UserInfoRepository
import javax.inject.Inject

class RemoveSharedPreferenceDataUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {

    operator fun invoke(){
        userInfoRepository.removeAllSharedPreference()
    }

}