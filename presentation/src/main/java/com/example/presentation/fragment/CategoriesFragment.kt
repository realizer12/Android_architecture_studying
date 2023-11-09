package com.example.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.base.base.fragment.BaseFragment
import com.example.util.const.Const
import com.example.presentation.databinding.FragmentCategoriesBinding
import com.example.presentation.enums.Category
import com.example.presentation.util.Util.navigateWithAnim

class CategoriesFragment: BaseFragment<FragmentCategoriesBinding>(R.layout.fragment_categories) {

    override fun FragmentCategoriesBinding.onCreateView() {
        initSet()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEventListener()
    }
    private fun initSet(){

    }

    private fun setEventListener(){

        //category 별  버튼 클릭 리스너 달아줌.
        Category.values().forEach { category->
            view?.findViewById<AppCompatTextView>(category.viewId)?.setOnClickListener {
                findNavController().navigateWithAnim(R.id.categoryTopNewsFragment, Bundle().apply {
                    putString(Const.PARAM_ARTICLE_CATEGORY,category.queryString)//선택한 카테고리 보냄.
                })
            }
        }
    }

}