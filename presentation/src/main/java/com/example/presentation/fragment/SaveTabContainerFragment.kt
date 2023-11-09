package com.example.presentation.fragment

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.base.base.fragment.BaseFragment
import com.example.presentation.R
import com.example.presentation.databinding.FragmentSaveTabContainerBinding
import dagger.hilt.android.AndroidEntryPoint


class SaveTabContainerFragment:BaseFragment<FragmentSaveTabContainerBinding>(R.layout.fragment_save_tab_container) {
    override fun FragmentSaveTabContainerBinding.onCreateView() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_save) as NavHostFragment
    }
}