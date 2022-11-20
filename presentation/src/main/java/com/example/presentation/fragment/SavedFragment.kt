package com.example.presentation.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R
import com.example.presentation.adapter.TopNewsListAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentSavedBinding
import com.example.data.model.ArticleDataModel
import com.example.data.repository.news.TopNewsRepository
import com.example.data.repository.news.TopNewsRepositoryImpl
import com.example.remote.retrofit.RetrofitHelper
import com.example.local.room.LocalDataBase
import com.example.local.feature.news.impl.SavedNewsLocalDataSourceImpl
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.remote.feature.news.impl.TopNewsRemoteDataSourceImpl
import com.example.presentation.util.Util.navigateWithAnim
import com.example.util.const.Const
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class SavedFragment:BaseFragment<FragmentSavedBinding>(R.layout.fragment_saved) {

    //네비게이션 컨트롤러
    private lateinit var navController: NavController
    private lateinit var navHost: NavHostFragment

    private var rcyScrollLState: Parcelable? = null
    lateinit var topNewsListAdapter: TopNewsListAdapter

    //respository 가져옴
    private val topNewsRepository: TopNewsRepository by lazy{
        val topNewsRemoteDataSource = TopNewsRemoteDataSourceImpl(RetrofitHelper)
        val savedNewsLocalDataSource = SavedNewsLocalDataSourceImpl(LocalDataBase.getInstance(requireActivity()))
        TopNewsRepositoryImpl(topNewsRemoteDataSource,savedNewsLocalDataSource)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //스크롤 state 다시 넣어줌.
        rcyScrollLState = savedInstanceState?.getParcelable(PARAM_RCY_SCROLL_STATE)
    }

    override fun FragmentSavedBinding.onCreateView() {
        initSet()
        setListenerEvent()
    }
    private fun initSet(){

        navHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.findNavController()

        topNewsListAdapter = TopNewsListAdapter()
        binding.rvSavedNewsList.apply {
            adapter = topNewsListAdapter
        }

        setToolbar()
        getSavedNewsList()
    }



    //리스너 이벤트 모음
    private fun setListenerEvent() {
        topNewsListAdapter.setOnTopNewsItemClickListener(object :
            TopNewsListAdapter.ItemClickListener {
            override fun onTopNewItemClick(article: ArticlePresentationDataModel) {
                navController.navigateWithAnim(R.id.articleDetailFragment, Bundle().apply {
                    putParcelable(Const.PARAM_ARTICLE_MODEL,article)//닉네임 보냄
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
        outState.putParcelable(PARAM_RCY_SCROLL_STATE,rcyScrollLState)
    }

    private fun getSavedNewsList(){
        //저장 여부 체크
        topNewsRepository.getSavedArticleList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ articles ->
                topNewsListAdapter.submitList(articles.map { it.fromArticleData() })
                binding.rvSavedNewsList.layoutManager?.onRestoreInstanceState(rcyScrollLState)
            }, {
                showToast(it.message.toString())
            })
    }


    //toolbar setting
    private fun setToolbar(){
        binding.toolbar.tvTitle.text = getString(R.string.saved)
    }

    companion object{
        const val PARAM_RCY_SCROLL_STATE = "param_rcy_scroll_state"
    }
}