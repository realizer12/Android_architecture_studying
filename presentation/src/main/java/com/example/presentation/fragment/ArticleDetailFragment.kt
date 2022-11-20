package com.example.presentation.fragment

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentArticleDetailBinding
import com.example.data.model.ArticleDataModel
import com.example.data.repository.news.TopNewsRepository
import com.example.data.repository.news.TopNewsRepositoryImpl
import com.example.remote.retrofit.RetrofitHelper
import com.example.local.room.LocalDataBase
import com.example.local.feature.news.impl.SavedNewsLocalDataSourceImpl
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.remote.feature.news.impl.TopNewsRemoteDataSourceImpl
import com.example.presentation.util.Util.checkTimePassed
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ArticleDetailFragment :
    BaseFragment<FragmentArticleDetailBinding>(R.layout.fragment_article_detail) {

    private var article: ArticlePresentationDataModel? = null

    //네비게이션 컨트롤러
    private lateinit var navController: NavController
    private lateinit var navHost: NavHostFragment

    override fun FragmentArticleDetailBinding.onCreateView() {
        initSet()
        setListenerEvent()
    }

    //respository 가져옴
    private val topNewsRepository: TopNewsRepository by lazy {
        val topNewsRemoteDataSource = TopNewsRemoteDataSourceImpl(RetrofitHelper)
        val savedNewsLocalDataSource =
            SavedNewsLocalDataSourceImpl(LocalDataBase.getInstance(requireActivity()))
        TopNewsRepositoryImpl(topNewsRemoteDataSource, savedNewsLocalDataSource)
    }

    //화면실행시 맨처음에는 navigation 실행시 option으로 줬던  enter 애니메이션을 시작하고,
    //그외에는 stationay를 주어 enteranimation을 없애준다.-> 계속 메인 탭 이동시  이미 navigate된 fragment가 기존 설정한
    //enter animation을 실행하여서  이렇게 예외처리 해줌.
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if ((enter && arguments?.getBoolean(
                com.example.util.const.Const.PARAM_SCREEN_INITIALIZED,
                false
            ) == true)
        ) {
            AnimationUtils.loadAnimation(context, R.anim.stationary)
        } else {
            arguments?.putBoolean(com.example.util.const.Const.PARAM_SCREEN_INITIALIZED, true)
            null
        }
    }

    private fun initSet() {

        //article 데이터 넘겨 받음.
        article = arguments?.getParcelable(com.example.util.const.Const.PARAM_ARTICLE_MODEL)

        navHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.findNavController()


        //저장 여부 체크
        checkSavedArticle()

        //뷰에 값 세팅
        if (article != null) {

            binding.toolbar.tvTitle.text = article?.title ?: ""
            binding.tvAuthor.text = article?.author ?: "unknown writer"
            binding.tvNewsTitle.text = article?.title ?: ""
            binding.tvNewsContent.text = article?.content ?: ""
            binding.tvPublishTime.text = article?.publishedAt?.checkTimePassed()

            //썸네일 이미지 적용
            Glide.with(requireActivity())
                .load(article?.urlToImage)
                .into(binding.ivNewsThumbnail)
        }
    }


    //저장한 article 인지 여부를 체크 한다.
    private fun checkSavedArticle() {

        //저장 여부 체크
        topNewsRepository.getSavedArticleList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ articles ->
                setSaveIconVisible(isSaveStatus = articles.any { it.title == article?.title && it.publishedAt == article?.publishedAt && it.url == article?.url })
            }, {
                showToast(it.message.toString())
            })
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
            if (article == null) {
                return@setOnClickListener
            }

            topNewsRepository.removeArticle(article = article?.toArticleData()?:return@setOnClickListener)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setSaveIconVisible(isSaveStatus = false)
                }, {
                    showToast(it.message.toString())
                })
        }

        //save 하기
        binding.ivIconNotSaved.setOnClickListener {
            if (article == null) {
                return@setOnClickListener
            }

            topNewsRepository.saveArticle(article = article?.toArticleData()?:return@setOnClickListener)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setSaveIconVisible(isSaveStatus = true)
                }, {
                    showToast(it.message.toString())
                })
        }

        //뒤로가기
        binding.toolbar.ivBackArrow.setOnClickListener {
            navController.popBackStack()
        }
    }
}