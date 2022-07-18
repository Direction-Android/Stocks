package uz.azim.stocks.ui.fragment.favourites

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import uz.azim.stocks.R
import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.databinding.FragmentStocksBinding
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.model.Quote
import uz.azim.stocks.ui.fragment.BaseFragment
import uz.azim.stocks.ui.fragment.favourites.adapter.FavoritesAdapter
import uz.azim.stocks.ui.fragment.main.MainFragmentDirections
import uz.azim.stocks.ui.fragment.stocks.listener.OnStockClickListener
import uz.azim.stocks.ui.vm.FavouritesFragmentViewModel
import uz.azim.stocks.util.getDrawable
import uz.azim.stocks.util.navigate
import uz.azim.stocks.util.vmfactories.FavouritesFragmentViewModelFactory

class FavouritesFragment : BaseFragment<FragmentStocksBinding>(R.layout.fragment_stocks) {

    private val favouritesFragmentViewModel by viewModels<FavouritesFragmentViewModel> {
        FavouritesFragmentViewModelFactory()
    }
    private val favoritesAdapter: FavoritesAdapter = FavoritesAdapter()

    override fun initViewBinding(view: View) = FragmentStocksBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        setUpListeners()
        lifecycleScope.launchWhenStarted {
            getFavorites()
                .onStart {
                    showLoading()
                }
                .catch {
                    binding.retryBtn.isVisible = true
                    binding.progress.isVisible = false
                }
                .collect { list ->
                    hideLoading()
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

    private fun getFavorites() = favouritesFragmentViewModel.getFavouritesFlow()

    private fun setUpListeners() {
        binding.apply {
            retryBtn.setOnClickListener {
                lifecycleScope.launchWhenStarted {
                    getFavorites()
                        .onStart {
                            showLoading()
                        }
                        .catch {
                            binding.retryBtn.isVisible = true
                            binding.progress.isVisible = false
                        }
                        .collect { list ->
                            hideLoading()
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
                    favouritesFragmentViewModel.deleteStock(quote)
                }
            }

            override fun onStockClick(quote: Quote) {
                val action = MainFragmentDirections.actionMainFragmentToQuoteFragment(quote.symbol)
                navigate(R.id.mainFragment, action)
            }
        })
    }

    private suspend fun showLoading() {
        withContext(Dispatchers.Main) {
            binding.retryBtn.isVisible = false
            binding.progress.isVisible = true
        }
    }

    private suspend fun hideLoading() {
        withContext(Dispatchers.Main) {
            binding.retryBtn.isVisible = false
            binding.progress.isVisible = false
        }
    }

    override fun beforeDestroyBinding() {
        super.beforeDestroyBinding()
        //To prevent memory leak
        binding.rvStocks.adapter = null
    }
}
