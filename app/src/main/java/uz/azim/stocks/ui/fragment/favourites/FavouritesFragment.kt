package uz.azim.stocks.ui.fragment.favourites

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import uz.azim.stocks.R
import uz.azim.stocks.databinding.FragmentStocksBinding
import uz.azim.stocks.model.Quote
import uz.azim.stocks.ui.fragment.BaseFragment
import uz.azim.stocks.ui.fragment.favourites.adapter.FavoritesAdapter
import uz.azim.stocks.ui.fragment.favourites.vm.FavoriteVM
import uz.azim.stocks.ui.fragment.main.MainFragmentDirections
import uz.azim.stocks.ui.fragment.stocks.listener.OnStockClickListener
import uz.azim.stocks.util.getDrawable
import uz.azim.stocks.util.loading.Loading
import uz.azim.stocks.util.navigate
import uz.azim.stocks.util.viewModelFactrory.FavoriteViewModelFactory

class FavouritesFragment : BaseFragment<FragmentStocksBinding>(R.layout.fragment_stocks) {

    private val favoritesVM by viewModels<FavoriteVM> { FavoriteViewModelFactory() }
    private var loadingUtil = Loading()
    private val favoritesAdapter: FavoritesAdapter = FavoritesAdapter()

    override fun initViewBinding(view: View) = FragmentStocksBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        setUpListeners()
        lifecycleScope.launchWhenStarted {
            getFavorites()
                .onStart {
                    loadingUtil.showLoading(binding.progress, binding.retryBtn)
                }
                .catch {
                    binding.retryBtn.isVisible = true
                    binding.progress.isVisible = false
                }
                .collect { list ->
                    loadingUtil.hideLoading(binding.progress, binding.retryBtn)
                    favoritesAdapter.submitList(list)
                }
        }
    }

    private fun setUpRv() {
        binding.rvStocks.apply {
            adapter = favoritesAdapter
            layoutAnimation =
                AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.item_layout_anim)
        }

        favoritesAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun getFavorites() = favoritesVM.getAllFaves()

    private fun setUpListeners() {
        binding.apply {
            retryBtn.setOnClickListener {
                lifecycleScope.launchWhenStarted {
                    getFavorites()
                        .onStart {
                            loadingUtil.showLoading(progress, retryBtn)
                        }
                        .catch {
                            loadingUtil.notShowLoading(progress, retryBtn)
                        }
                        .collect { list ->
                            loadingUtil.hideLoading(progress, retryBtn)
                            favoritesAdapter.submitList(list)
                        }
                }
            }
        }

        val imgUnFav = getDrawable(requireContext(), R.drawable.ic_star_inactive)
        favoritesAdapter.onStockClickListener(object : OnStockClickListener {
            override fun onFavoriteClick(quote: Quote, imgFav: ImageView) {
                imgFav.setImageDrawable(imgUnFav)
                lifecycleScope.launchWhenStarted {
                    favoritesVM.deleteStock(quote)
                }
            }

            override fun onStockClick(quote: Quote) {
                val action = MainFragmentDirections.actionMainFragmentToQuoteFragment(quote.symbol)
                navigate(R.id.mainFragment, action)
            }
        })
    }

    override fun beforeDestroyBinding() {
        super.beforeDestroyBinding()
        //To prevent memory leak
        binding.rvStocks.adapter = null
    }
}
