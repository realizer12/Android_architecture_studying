package com.example.presentation.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.base.base.fragment.BaseFragment
import com.example.presentation.R
import com.example.presentation.activity.MainActivity
import com.example.presentation.adapter.TopNewsListAdapter
import com.example.presentation.databinding.FragmentCategoryTopNewsBinding
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.base.base.util.SingleEventObserver
import com.example.presentation.util.Util.navigateWithAnim
import com.example.presentation.viewmodel.CategoryTopNewsViewModel
import com.example.util.const.Const
import com.example.util.const.Const.PARAM_ARTICLE_MODEL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryTopNewsFragment : BaseFragment<FragmentCategoryTopNewsBinding>(R.layout.fragment_category_top_news) {

    //네비게이션 컨트롤러
    private lateinit var navController: NavController
    private lateinit var navHost: NavHostFragment
    private var rcyScrollLState: Parcelable? = null
    lateinit var topNewsListAdapter: TopNewsListAdapter

    private val categoryTopNewsViewModel: CategoryTopNewsViewModel by viewModels()


    //화면실행시 맨처음에는 navigation 실행시 option으로 줬던  enter 애니메이션을 시작하고,
    //그외에는 stationay를 주어 enteranimation을 없애준다.-> 계속 메인 탭 이동시  이미 navigate된 fragment가 기존 설정한
    //enter animation을 실행하여서  이렇게 예외처리 해줌.
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if ((enter && arguments?.getBoolean(
                Const.PARAM_SCREEN_INITIALIZED,
                false
            ) == true)
        ) {
            AnimationUtils.loadAnimation(context, R.anim.stationary)
        } else {
            arguments?.putBoolean(Const.PARAM_SCREEN_INITIALIZED, true)
            null
        }
    }


    override fun FragmentCategoryTopNewsBinding.onCreateView() {
        initSet()
        getDataFromVm()
        setListenerEvent()
    }

    //툴바 세팅
    private fun setToolbar() {
        binding.toolbar.root.visibility = View.INVISIBLE
        binding.toolbarBack.root.visibility = View.VISIBLE
    }

    private fun initSet() {
        setToolbar()

        navHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.findNavController()

        binding.categoryTopNewsListener = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.categoryTopNewsViewModel = categoryTopNewsViewModel
        binding.parentActivity = requireActivity() as MainActivity

        topNewsListAdapter = TopNewsListAdapter()
        binding.rvTopNewsList.apply {
            adapter = topNewsListAdapter
        }

        binding.rvTopNewsList.layoutManager?.onRestoreInstanceState(rcyScrollLState)

    }

    //리스너 이벤트 모음
    private fun setListenerEvent() {
        topNewsListAdapter.setOnTopNewsItemClickListener(object :
            TopNewsListAdapter.ItemClickListener {
            override fun onTopNewItemClick(article: ArticlePresentationDataModel) {
                navController.navigateWithAnim(R.id.articleDetailFragment, Bundle().apply {
                    putParcelable(PARAM_ARTICLE_MODEL, article)//닉네임 보냄
                })
            }
        })

        binding.rvTopNewsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // 마지막에 보이는 포지션
                val lastVisiblePosition =
                    (binding.rvTopNewsList.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                val lastPosition = topNewsListAdapter.itemCount - 1

                //리사이클러뷰 스크롤 위치 뷰모델에 캐싱 -> 유지하기 위해서
                rcyScrollLState =
                    binding.rvTopNewsList.layoutManager?.onSaveInstanceState()

                // 스크롤이 끝에 도달했는지 확인후  api를 요청해서 다음 페이지를 받아온다.
                if (!recyclerView.canScrollVertically(1)
                    && lastVisiblePosition == lastPosition
                ) {
                    categoryTopNewsViewModel.getCategoryTopNewsList()
                }
            }
        })
    }


    //뷰모델에서 데이터 받을때 처리
    private fun getDataFromVm() {

        //에러 받아와서 토스트 처리
        categoryTopNewsViewModel.errorToast.observe(viewLifecycleOwner, SingleEventObserver {
            showToast(it.message.toString())
        })
    }

}