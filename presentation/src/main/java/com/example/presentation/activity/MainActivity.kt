package com.example.presentation.activity

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.presentation.R
import com.example.base.base.activity.BaseActivity
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.enums.MainEnum
import com.example.presentation.fragment.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    //네비게이션 컨트롤러

    private val mainViewModel:MainViewModel by viewModels()


    override fun ActivityMainBinding.onCreate() {
        setBottomNavigation()


    }

    override fun onBackPressed() {
       if( supportFragmentManager.fragments.find { it.isVisible }?.tag == MainEnum.TOP_NEWS.tag){
           super.onBackPressed()
       }else{
           binding.bnMenu.selectedItemId = R.id.main_top_news_nav_graph
       }
    }


    //바텀 메뉴 세팅
    private fun setBottomNavigation(){
        binding.bnMenu.setOnItemSelectedListener {
            mainViewModel.setCurrentPage(it.itemId)
            true
        }

        mainViewModel.currentPageType.observe(this@MainActivity) {
            // init fragment
            changeFragment(it)
        }
    }
    private fun changeFragment(pageType: MainEnum) {
        val transaction = supportFragmentManager.beginTransaction()

        var targetFragment = supportFragmentManager.findFragmentByTag(pageType.tag)

        if (targetFragment == null) {
            targetFragment = getFragment(pageType)
            transaction.add(R.id.container, targetFragment, pageType.tag)
        }
        transaction.show(targetFragment)
        MainEnum.values()
            .filterNot { it == pageType }
            .forEach { type ->
                supportFragmentManager.findFragmentByTag(type.tag)?.let {
                    transaction.hide(it)
                }
            }
        transaction.commitAllowingStateLoss()
    }

    private fun getFragment(pageType: MainEnum): Fragment {
        return pageType.fragment
    }

//    private fun isNavigationHasBackStack(): Boolean {
//
//        //현재 navigation currentdestination이 메인 바텀 메뉴 3개중 하나리면, false를 리턴한다.
//        return when (navController.currentDestination?.id) {
//            R.id.topNewsFragment, R.id.categoriesFragment, R.id.savedFragment-> {
//                false
//            }
//            else -> {
//                true
//            }
//        }
//    }
}
