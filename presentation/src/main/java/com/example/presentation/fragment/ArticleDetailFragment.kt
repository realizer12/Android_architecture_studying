package com.example.presentation.fragment

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.data.repository.news.TopNewsRepository
import com.example.data.repository.news.TopNewsRepositoryImpl
import com.example.local.feature.news.impl.SavedNewsLocalDataSourceImpl
import com.example.local.room.LocalDataBase
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentArticleDetailBinding
import com.example.presentation.util.Util.checkTimePassed
import com.example.presentation.viewmodel.ArticleDetailViewModel
import com.example.presentation.viewmodel.factory.ArticleDetailViewModelFactory
import com.example.remote.feature.news.impl.TopNewsRemoteDataSourceImpl
import com.example.remote.retrofit.RetrofitHelper
import com.example.util.const.Const

class ArticleDetailFragment :
    BaseFragment<FragmentArticleDetailBinding>(R.layout.fragment_article_detail) {

    //네비게이션 컨트롤러
    private lateinit var navController: NavController
    private lateinit var navHost: NavHostFragment

    override fun FragmentArticleDetailBinding.onCreateView() {
        initSet()
        setListenerEvent()
        getDataFromVm()
    }

    //respository 가져옴
    private val topNewsRepository: TopNewsRepository by lazy {
        val topNewsRemoteDataSource = TopNewsRemoteDataSourceImpl(RetrofitHelper)
        val savedNewsLocalDataSource =
            SavedNewsLocalDataSourceImpl(LocalDataBase.getInstance(requireActivity()))
        TopNewsRepositoryImpl(topNewsRemoteDataSource, savedNewsLocalDataSource)
    }


    private val articleDetailViewModel: ArticleDetailViewModel by lazy {
        ViewModelProvider(
            owner = this,
            factory = ArticleDetailViewModelFactory(repository = topNewsRepository)
        )[ArticleDetailViewModel::class.java]
    }



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

    }


    //save 뷰 상태 체크
    private fun setSaveIconVisible(isSaveStatus: Boolean) {
        if (isSaveStatus) {
            binding.ivIconSaved.visibility = View.VISIBLE
            binding.ivIconNotSaved.visibility = View.GONE
        } else {
            binding.ivIconSaved.visibility = View.GONE
            binding.ivIconNotSaved.visibility = View.VISIBLE
        }
    }

    //리스너 이벤트 모음
    private fun setListenerEvent() {

        //save 취소
        binding.ivIconSaved.setOnClickListener {
//            if (article == null) {
//                return@setOnClickListener
//            }
//
//            topNewsRepository.removeArticle(article = article?.toArticleData()?:return@setOnClickListener)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    setSaveIconVisible(isSaveStatus = false)
//                }, {
//                    showToast(it.message.toString())
//                })
        }

        //save 하기
        binding.ivIconNotSaved.setOnClickListener {
//            if (article == null) {
//                return@setOnClickListener
//            }
//
//            topNewsRepository.saveArticle(article = article?.toArticleData()?:return@setOnClickListener)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    setSaveIconVisible(isSaveStatus = true)
//                }, {
//                    showToast(it.message.toString())
//                })
        }

        //뒤로가기
        binding.toolbar.ivBackArrow.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun getDataFromVm(){
        articleDetailViewModel.isSaveArticle.subscribe { isSaveStatus ->
            setSaveIconVisible(isSaveStatus = isSaveStatus)
        }

        //에러가 나왔을떄
        articleDetailViewModel.errorPublishSubject.subscribe {
            showToast(it.message.toString())
        }


        articleDetailViewModel.detailArticle.subscribe { article ->
            binding.toolbar.tvTitle.text = article.title ?: ""
            binding.tvAuthor.text = article.author ?: "unknown writer"
            binding.tvNewsTitle.text = article.title ?: ""
            binding.tvNewsContent.text = article.content ?: ""
            binding.tvPublishTime.text = article.publishedAt?.checkTimePassed()

            //썸네일 이미지 적용
            Glide.with(requireActivity())
                .load(article.urlToImage)
                .into(binding.ivNewsThumbnail)
        }

    }
}