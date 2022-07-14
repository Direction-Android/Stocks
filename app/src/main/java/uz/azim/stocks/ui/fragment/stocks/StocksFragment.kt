package uz.azim.stocks.ui.fragment.stocks

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collect
import uz.azim.stocks.R
import uz.azim.stocks.databinding.FragmentStocksBinding
import uz.azim.stocks.model.Quote
import uz.azim.stocks.ui.fragment.BaseFragment
import uz.azim.stocks.ui.fragment.main.MainFragmentDirections
import uz.azim.stocks.ui.fragment.stocks.adapter.LoadFooterAdapter
import uz.azim.stocks.ui.fragment.stocks.adapter.QuoteAdapter
import uz.azim.stocks.ui.fragment.stocks.listener.OnStockClickListener
import uz.azim.stocks.ui.fragment.stocks.vm.StocksVM
import uz.azim.stocks.util.Resource
import uz.azim.stocks.util.anim.LikeAnimation
import uz.azim.stocks.util.log
import uz.azim.stocks.util.navigate

class StocksFragment : BaseFragment<FragmentStocksBinding>(R.layout.fragment_stocks) {

    private val quoteAdapter: QuoteAdapter = QuoteAdapter()
    private val stocksVM by viewModels<StocksVM>()

    override fun initViewBinding(view: View) = FragmentStocksBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        setUpListeners()
    }


    override fun subscribeObservers() {
        lifecycleScope.launchWhenStarted {
            stocksVM.quotes.collect {
                if (it is Resource.Success) {
                    it.data?.let { list ->
                        quoteAdapter.submitData(list)
                    }
                }
            }
        }
    }

    private fun setUpRv() {
        binding.rvStocks.apply {
            adapter =
                quoteAdapter.withLoadStateFooter(LoadFooterAdapter { quoteAdapter.retry() })
            layoutAnimation =
                AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.item_layout_anim)
        }
    }

    private fun setUpListeners() {
        val likeAnim = LikeAnimation(requireContext())
        quoteAdapter.onStockClickListener(object : OnStockClickListener {

            override fun onFavoriteClick(quote: Quote, imgFav: ImageView) {
                if (quote.isFavorite) {
                    likeAnim.unlike(imgFav)
                    stocksVM.deleteStock(quote)
                } else {
                    likeAnim.like(imgFav)
                    stocksVM.saveStock(quote)
                }
                quote.isFavorite = !quote.isFavorite
            }

            override fun onStockClick(quote: Quote) {
                val action = MainFragmentDirections.actionMainFragmentToQuoteFragment(quote.symbol)
                navigate(R.id.mainFragment, action)
            }
        })

        quoteAdapter.addLoadStateListener { loadState ->
            binding.rvStocks.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progress.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryBtn.isVisible = loadState.source.refresh is LoadState.Error
            if (loadState.source.refresh is LoadState.Error) {
                val er = loadState.source.refresh as LoadState.Error
                log("${er.error}")
            }
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "Error fetching",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        binding.retryBtn.setOnClickListener {
            quoteAdapter.refresh()
        }
    }


    override fun beforeDestroyBinding() {
        super.beforeDestroyBinding()
        //To prevent memory leak
        binding.rvStocks.adapter = null
    }
}
