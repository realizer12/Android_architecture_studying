package com.example.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.repository.news.TopNewsRepository
import com.example.data.repository.news.TopNewsRepositoryImpl
import com.example.local.PreferenceManager
import com.example.local.feature.news.impl.SavedNewsLocalDataSourceImpl
import com.example.local.room.LocalDataBase
import com.example.presentation.R
import com.example.presentation.activity.SplashActivity
import com.example.presentation.adapter.TopNewsListAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentTopNewsBinding
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.presentation.util.SingleEventObserver
import com.example.presentation.util.Util.navigateWithAnim
import com.example.presentation.viewmodel.TopNewsViewModel
import com.example.presentation.viewmodel.factory.ViewModelFactory
import com.example.remote.feature.news.impl.TopNewsRemoteDataSourceImpl

class TopNewsFragment : BaseFragment<FragmentTopNewsBinding>(R.layout.fragment_top_news) {

    lateinit var topNewsListAdapter: TopNewsListAdapter

    private var rcyScrollLState: Parcelable? = null

    //네비게이션 컨트롤러
    private lateinit var navController: NavController
    private lateinit var navHost: NavHostFragment

    //reposotory 구성 해줌.
    private val topNewsRepository: TopNewsRepository by lazy {
        val topNewsRemoteDataSource = TopNewsRemoteDataSourceImpl(RetrofitHelper)
        val topNewsLocalDataSource = SavedNewsLocalDataSourceImpl(
            LocalDataBase.getInstance(requireActivity().applicationContext)
        )
        TopNewsRepositoryImpl(topNewsRemoteDataSource, topNewsLocalDataSource)
    }

    private val topNewsViewModel: TopNewsViewModel by viewModels {
        ViewModelFactory(repository = topNewsRepository)
    }

    override fun FragmentTopNewsBinding.onCreateView() {
        initSet()
        setListenerEvent()
        getDataFromVm()
    }

    private fun initSet() {

        navHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.findNavController()

        binding.topNewsListener = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.topNewsViewModel = topNewsViewModel

        topNewsListAdapter = TopNewsListAdapter()
        binding.rvTopNewsList.apply {
            adapter = topNewsListAdapter
        }
        binding.rvTopNewsList.layoutManager?.onRestoreInstanceState(rcyScrollLState)
    }


    //로그아웃 처리
    fun logout() {
        LocalDataBase.destroyInstance()
        PreferenceManager.removeAllPreference(requireActivity())//로그인 체크 값 다 지워줌.
        startActivity(Intent(requireActivity(), SplashActivity::class.java))
        requireActivity().finish()
    }

    //리스너 이벤트 모음
    private fun setListenerEvent() {
        topNewsListAdapter.setOnTopNewsItemClickListener(object :
            TopNewsListAdapter.ItemClickListener {
            override fun onTopNewItemClick(article: ArticlePresentationDataModel) {
                navController.navigateWithAnim(R.id.articleDetailFragment, Bundle().apply {
                    putParcelable(com.example.util.const.Const.PARAM_ARTICLE_MODEL, article)//닉네임 보냄
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
                    topNewsViewModel.getTopNewList()
                }
            }
        })

    }

    private fun getDataFromVm() {

        //에러가 나왔을떄
        topNewsViewModel.errorToast.observe(viewLifecycleOwner,SingleEventObserver{
            showToast(it.message.toString())
        })

    }

}