package uz.azim.stocks.ui.fragment.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import uz.azim.stocks.R
import uz.azim.stocks.databinding.FragmentSearchBinding
import uz.azim.stocks.ui.fragment.BaseFragment
import uz.azim.stocks.ui.fragment.search.adapter.SearchAdapter
import uz.azim.stocks.ui.fragment.search.vm.SearchVM
import uz.azim.stocks.util.hideKeyboard
import uz.azim.stocks.util.loading.Loading
import uz.azim.stocks.util.navigate
import uz.azim.stocks.util.viewModelFactrory.SearchViewModelFactory

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val searchVM by viewModels<SearchVM> { SearchViewModelFactory() }
    private val loadingUtil = Loading()

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
            searchVM.getSearchResult(query)
                .onStart { loadingUtil.showLoading(binding.progress, binding.rvSearch) }
                .catch { loadingUtil.hideLoading(binding.progress, binding.rvSearch) }
                .collect {
                    loadingUtil.hideLoading(binding.progress, binding.rvSearch)
                    it.data?.let { list ->
                        searchAdapter.submitList(list)
                    }
                }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(searchText, binding.searchBox.getText())
    }

}
