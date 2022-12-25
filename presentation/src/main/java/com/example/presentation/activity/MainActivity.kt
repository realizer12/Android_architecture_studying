package com.example.presentation.activity

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.presentation.R
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    //네비게이션 컨트롤러
    lateinit var navController:NavController

    override fun ActivityMainBinding.onCreate() {
        setBottomNavigation()
    }

    override fun onBackPressed() {
        if(isNavigationHasBackStack()){
            navController.popBackStack()
        }else{
            if (navController.currentDestination?.id != R.id.topNewsFragment) {
                binding.bnMenu.selectedItemId = R.id.main_top_news_nav_graph
            } else {
                super.onBackPressed()
            }
        }
    }


    //바텀 메뉴 세팅
    private fun setBottomNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()//네비게시션 컨트롤러
        binding.bnMenu.setupWithNavController(navController)//바텀 네비게이션에  컨트롤러 연결
    }


    private fun isNavigationHasBackStack(): Boolean {

        //현재 navigation currentdestination이 메인 바텀 메뉴 3개중 하나리면, false를 리턴한다.
        return when (navController.currentDestination?.id) {
            R.id.topNewsFragment, R.id.categoriesFragment, R.id.savedFragment-> {
                false
            }
            else -> {
                true
            }
        }
    }
}
