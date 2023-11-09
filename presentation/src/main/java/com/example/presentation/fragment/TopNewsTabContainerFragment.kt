package com.example.presentation.fragment

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.base.base.fragment.BaseFragment
import com.example.presentation.R
import com.example.presentation.databinding.FragmentTopNewsTabContainerBinding
import dagger.hilt.android.AndroidEntryPoint


class TopNewsTabContainerFragment:BaseFragment<FragmentTopNewsTabContainerBinding>(R.layout.fragment_top_news_tab_container) {

    //네비게이션 컨트롤러
    private lateinit var navController: NavController
    private lateinit var navHost: NavHostFragment
    override fun FragmentTopNewsTabContainerBinding.onCreateView() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_top_news) as NavHostFragment
        navController = navHostFragment.findNavController()//네비게시션 컨트롤러
    }
}