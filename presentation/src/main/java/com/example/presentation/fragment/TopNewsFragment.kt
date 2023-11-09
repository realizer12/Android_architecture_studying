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
import com.example.base.base.fragment.BaseFragment
import com.example.presentation.R
import com.example.presentation.activity.SplashActivity
import com.example.presentation.adapter.TopNewsListAdapter
import com.example.presentation.databinding.FragmentTopNewsBinding
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.base.base.util.SingleEventObserver
import com.example.presentation.util.Util.navigateWithAnim
import com.example.presentation.viewmodel.TopNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopNewsFragment : BaseFragment<FragmentTopNewsBinding>(R.layout.fragment_top_news) {

    lateinit var topNewsListAdapter: TopNewsListAdapter

    private var rcyScrollLState: Parcelable? = null

    private val topNewsViewModel: TopNewsViewModel by viewModels()

    override fun FragmentTopNewsBinding.onCreateView() {
        initSet()
        setListenerEvent()
        getDataFromVm()
    }

    private fun initSet() {



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
        topNewsViewModel.logoutProcess()
        startActivity(Intent(requireActivity(), SplashActivity::class.java))
        requireActivity().finish()
    }

    //리스너 이벤트 모음
    private fun setListenerEvent() {
        topNewsListAdapter.setOnTopNewsItemClickListener(object :
            TopNewsListAdapter.ItemClickListener {
            override fun onTopNewItemClick(article: ArticlePresentationDataModel) {
                findNavController().navigateWithAnim(R.id.articleDetailFragment, Bundle().apply {
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
        topNewsViewModel.errorToast.observe(viewLifecycleOwner, SingleEventObserver{
            showToast(it.message.toString())
        })

    }

}