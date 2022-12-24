package com.example.presentation.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.data.repository.news.TopNewsRepository
import com.example.data.repository.news.TopNewsRepositoryImpl
import com.example.local.feature.news.impl.SavedNewsLocalDataSourceImpl
import com.example.local.room.LocalDataBase
import com.example.presentation.R
import com.example.presentation.adapter.TopNewsListAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentSavedBinding
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.presentation.util.SingleEventObserver
import com.example.presentation.util.Util.navigateWithAnim
import com.example.presentation.viewmodel.SavedViewModel
import com.example.presentation.viewmodel.factory.ViewModelFactory
import com.example.remote.feature.news.impl.TopNewsRemoteDataSourceImpl
import com.example.remote.retrofit.RetrofitHelper
import com.example.util.const.Const

class SavedFragment : BaseFragment<FragmentSavedBinding>(R.layout.fragment_saved) {

    //네비게이션 컨트롤러
    private lateinit var navController: NavController
    private lateinit var navHost: NavHostFragment

    private var rcyScrollLState: Parcelable? = null
    lateinit var topNewsListAdapter: TopNewsListAdapter

    //respository 가져옴
    private val topNewsRepository: TopNewsRepository by lazy {
        val topNewsRemoteDataSource = TopNewsRemoteDataSourceImpl(RetrofitHelper)
        val savedNewsLocalDataSource =
            SavedNewsLocalDataSourceImpl(LocalDataBase.getInstance(requireActivity()))
        TopNewsRepositoryImpl(topNewsRemoteDataSource, savedNewsLocalDataSource)
    }

    private val savedViewModel: SavedViewModel by viewModels{
        ViewModelFactory(repository = topNewsRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //스크롤 state 다시 넣어줌.
        rcyScrollLState = savedInstanceState?.getParcelable(PARAM_RCY_SCROLL_STATE)
    }

    override fun FragmentSavedBinding.onCreateView() {
        initSet()
        getDataFromVm()
        setListenerEvent()
    }

    private fun initSet() {

        navHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.findNavController()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.savedViewModel = savedViewModel

        topNewsListAdapter = TopNewsListAdapter()
        binding.rvSavedNewsList.apply {
            adapter = topNewsListAdapter
        }
        savedViewModel.getSavedNewsList()
    }


    //리스너 이벤트 모음
    private fun setListenerEvent() {
        topNewsListAdapter.setOnTopNewsItemClickListener(object :
            TopNewsListAdapter.ItemClickListener {
            override fun onTopNewItemClick(article: ArticlePresentationDataModel) {
                navController.navigateWithAnim(R.id.articleDetailFragment, Bundle().apply {
                    putParcelable(Const.PARAM_ARTICLE_MODEL, article)//닉네임 보냄
                })
            }
        })

        binding.rvSavedNewsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //리사이클러뷰 스크롤 위치 뷰모델에 캐싱 -> 유지하기 위해서
                rcyScrollLState =
                    binding.rvSavedNewsList.layoutManager?.onSaveInstanceState()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //햔제 스크롤 포지션 저장
        outState.putParcelable(PARAM_RCY_SCROLL_STATE, rcyScrollLState)
    }


    private fun getDataFromVm() {
        savedViewModel.errorToast.observe(viewLifecycleOwner,SingleEventObserver{
            showToast(it.message.toString())
        })
    }

    companion object {
        const val PARAM_RCY_SCROLL_STATE = "param_rcy_scroll_state"
    }
}