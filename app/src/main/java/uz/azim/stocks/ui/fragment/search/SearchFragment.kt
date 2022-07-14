package uz.azim.stocks.ui.fragment.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import uz.azim.stocks.R
import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.databinding.FragmentSearchBinding
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.ui.fragment.BaseFragment
import uz.azim.stocks.ui.fragment.search.adapter.SearchAdapter
import uz.azim.stocks.util.hideKeyboard
import uz.azim.stocks.util.navigate

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val stocksRepository: StocksRepository = RepositoryModule.bindStockRepo()

    private val searchAdapter: SearchAdapter = SearchAdapter()
    private val searchText = "text"

    override fun initViewBinding(view: View) = FragmentSearchBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        savedInstanceState?.let { bundle ->
            val text = bundle.getString(searchText) ?: ""
            binding.searchBox.setText(text)
        }
        setUpRv()
        setUpListeners()
    }

    private fun setUpRv() {
        binding.rvSearch.adapter = searchAdapter
        searchAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun setUpListeners() {
        binding.searchBox.apply {
            onStartImgClicked {
                findNavController().popBackStack()
                it.hideKeyboard()
            }

            onTextChanged {
                searchForStock(it)
            }

        }
        searchAdapter.onStockClickListener {
            binding.root.hideKeyboard()
            val action =
                SearchFragmentDirections.actionSearchFragmentToQuoteFragment(it)
            navigate(R.id.searchFragment, action)
        }

    }

    private fun searchForStock(query: String) {
        lifecycleScope.launchWhenStarted {
            stocksRepository.searchStock(query)
                .onStart { showProgress() }
                .catch { hideProgress() }
                .collect {
                    hideProgress()
                    it.data?.let { list ->
                        searchAdapter.submitList(list)
                    }
                }
        }
    }

    private fun hideProgress() {
        binding.apply {
            progress.isVisible = false
            rvSearch.isVisible = true
        }
    }

    private fun showProgress() {
        binding.apply {
            progress.isVisible = true
            rvSearch.isVisible = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(searchText, binding.searchBox.getText())
    }

}
