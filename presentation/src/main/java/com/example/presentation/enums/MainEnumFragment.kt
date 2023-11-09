package com.example.presentation.enums

import androidx.fragment.app.Fragment
import com.example.presentation.fragment.CategoryTabContainerFragment
import com.example.presentation.fragment.SaveTabContainerFragment
import com.example.presentation.fragment.TopNewsTabContainerFragment

enum class MainEnum(val tag: String,val fragment: Fragment) {
    TOP_NEWS( "TOP_NEWS",TopNewsTabContainerFragment()),
    CATEGORIES( "CATEGORIES",CategoryTabContainerFragment()),
    SAVED( "SAVED",SaveTabContainerFragment());
}