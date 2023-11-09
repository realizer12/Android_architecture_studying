package com.example.presentation.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.NavHostFragment
import com.example.base.base.fragment.BaseFragment
import com.example.presentation.R
import com.example.presentation.databinding.FragmentCategoryTabContainerBinding
import com.example.presentation.util.Util.isNavigationHasBackStack
import dagger.hilt.android.AndroidEntryPoint

class CategoryTabContainerFragment:BaseFragment<FragmentCategoryTabContainerBinding>(R.layout.fragment_category_tab_container) {
    lateinit var navHostFragment: NavHostFragment

    override fun FragmentCategoryTabContainerBinding.onCreateView() {

        navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_category) as NavHostFragment
    }


    override fun onHiddenChanged(hidden: Boolean) {
        if(!hidden){
            binding.root.isFocusableInTouchMode = true;
            binding.root.requestFocus();
            binding.root.setOnKeyListener { v, keyCode, event ->

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (Pair(
                            R.id.categoriesFragment,
                            navHostFragment.navController
                        ).isNavigationHasBackStack()
                    ) {
                        navHostFragment.navController.popBackStack()
                    }else{
                        return@setOnKeyListener false
                    }
                    return@setOnKeyListener  true
                }
                else return@setOnKeyListener  false
            }
        }
        super.onHiddenChanged(hidden)
    }


    private fun initSet() {

    }


}
