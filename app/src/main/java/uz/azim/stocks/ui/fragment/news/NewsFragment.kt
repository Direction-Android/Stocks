package uz.azim.stocks.ui.fragment.news

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import uz.azim.stocks.R
import uz.azim.stocks.databinding.FragmentNewsBinding
import uz.azim.stocks.ui.fragment.BaseFragment
import uz.azim.stocks.ui.fragment.news.adapter.NewsAdapter
import uz.azim.stocks.ui.fragment.news.vm.NewsVM
import uz.azim.stocks.ui.fragment.quote.SYMBOL
import uz.azim.stocks.util.isNull
import uz.azim.stocks.util.loading.Loading
import uz.azim.stocks.util.viewModelFactrory.NewsViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class NewsFragment : BaseFragment<FragmentNewsBinding>(R.layout.fragment_news) {

    private lateinit var symbol: String
    private val newsVM by viewModels<NewsVM>{NewsViewModelFactory()}
    private val loadingUtil = Loading()
    private val newsAdapter: NewsAdapter = NewsAdapter()

    override fun initViewBinding(view: View) = FragmentNewsBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        symbol = arguments?.getString(SYMBOL) ?: ""
        if (savedInstanceState.isNull())
            getNews()
        setUpRv()
        setUpListeners()
    }

    private fun setUpRv() {
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutAnimation =
                AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.item_layout_anim)
        }
    }

    private fun setUpListeners() {
        binding.apply {
            btnRetry.setOnClickListener {
                getNews()
                btnRetry.isVisible = false
            }
            tvPs.setOnClickListener {
                val builder = CustomTabsIntent.Builder().build()
                builder.launchUrl(requireContext(), Uri.parse("https://finnhub.io/"))
            }
        }
        newsAdapter.onNewsClickListener { news ->
            val builder = CustomTabsIntent.Builder().build()
            builder.launchUrl(requireContext(), Uri.parse(news.url))
        }
    }

    private fun getNews() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.DAY_OF_YEAR, -NEWS_FILTER.WEEK)
        val fromDate = calendar.time
        loadingUtil.showLoading(binding.progress, binding.rvNews)

        lifecycleScope.launchWhenStarted {
            newsVM.companyNews(
                symbol,
                dateFormat.format(fromDate),
                dateFormat.format(currentDate),
            )
                .catch {
                    loadingUtil.hideLoading(binding.progress, binding.rvNews)
                    loadingUtil.showError(binding.progress, binding.rvNews, binding.btnRetry)
                }
                .collect {
                    loadingUtil.hideLoading(binding.progress, binding.rvNews)
                    newsAdapter.submitList(it.data)
                }
        }
    }
}

object NEWS_FILTER {
    const val WEEK = 7
    const val MONTH = 30
    const val YEAR = 360
}
