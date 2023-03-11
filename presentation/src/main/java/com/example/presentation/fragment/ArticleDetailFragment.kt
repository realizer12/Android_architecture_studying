package com.example.presentation.fragment

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.base.base.BaseFragment
import com.example.presentation.R
import com.example.presentation.activity.MainActivity
import com.example.presentation.databinding.FragmentArticleDetailBinding
import com.example.base.base.SingleEventObserver
import com.example.presentation.viewmodel.ArticleDetailViewModel
import com.example.util.const.Const
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment :
    BaseFragment<FragmentArticleDetailBinding>(R.layout.fragment_article_detail) {

    //네비게이션 컨트롤러
    private lateinit var navController: NavController
    private lateinit var navHost: NavHostFragment

    override fun FragmentArticleDetailBinding.onCreateView() {
        initSet()
        getDataFromVm()
    }

    private val articleDetailViewModel: ArticleDetailViewModel by viewModels()

    //화면실행시 맨처음에는 navigation 실행시 option으로 줬던  enter 애니메이션을 시작하고,
    //그외에는 stationay를 주어 enteranimation을 없애준다.-> 계속 메인 탭 이동시  이미 navigate된 fragment가 기존 설정한
    //enter animation을 실행하여서  이렇게 예외처리 해줌.
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if ((enter && arguments?.getBoolean(Const.PARAM_SCREEN_INITIALIZED, false) == true)
        ) {
            AnimationUtils.loadAnimation(context, R.anim.stationary)
        } else {
            arguments?.putBoolean(Const.PARAM_SCREEN_INITIALIZED, true)
            null
        }
    }

    private fun initSet() {

        navHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.findNavController()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vmArticleDetail = articleDetailViewModel
        binding.parentActivity = requireActivity() as MainActivity

        //각 탭별 연동을 위해 create 될때마다  article 체크를 진행한다.
        articleDetailViewModel.checkSavedArticle()
    }

    private fun getDataFromVm(){

        //에러가 나왔을떄
        articleDetailViewModel.errorToast.observe(viewLifecycleOwner, SingleEventObserver{
            showToast(it.message.toString())
        })

    }
}