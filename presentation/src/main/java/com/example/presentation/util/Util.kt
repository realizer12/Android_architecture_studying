package com.example.presentation.util

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.presentation.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


typealias DefaultFragmentId = Int

object Util {


    fun Pair<DefaultFragmentId,NavController>.isNavigationHasBackStack(): Boolean {
        //현재 navigation currentdestination이 메인 바텀 메뉴 3개중 하나리면, false를 리턴한다.
        return when (this.second.currentDestination?.id) {
            this.first -> {
                false
            }
            else -> {
                true
            }
        }
    }

    //시간 얼마나 지났는지를 체크
    fun String.checkTimePassed():String{

        val transFormat =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = transFormat.parse(this)

        val diff = (Date().time - date.time) / 1000
        return when (diff) {
            in 0 until 10 -> "now"
            in 10 until 60 -> "$diff seconds ago"
            in 60 until 60 * 60 -> "${diff/60} minutes ago"
            in 60 * 60 until 60 * 60 * 24 -> "${diff/3600} hours ago"
            else -> DateFormat.getDateInstance(DateFormat.SHORT).format(date).toString()
        }
    }

    //애니메이션 주면서 bundle 값 과 같이  navigation 이동할때 사용
    fun NavController.navigateWithAnim(destinationId: Int, bundle: Bundle){
        val options = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_from_right)
            .setExitAnim(R.anim.stationary)
            .setPopEnterAnim(R.anim.stationary)
            .setPopExitAnim(R.anim.slide_to_right)
            .build()
        this.navigate(destinationId,bundle,options)
    }

}