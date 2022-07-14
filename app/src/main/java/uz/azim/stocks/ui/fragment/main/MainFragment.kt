package uz.azim.stocks.ui.fragment.main

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import uz.azim.stocks.R
import uz.azim.stocks.databinding.FragmentMainBinding
import uz.azim.stocks.ui.fragment.BaseFragment
import uz.azim.stocks.ui.fragment.main.adapter.StocksPagerAdapter
import uz.azim.stocks.util.navigate

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override fun initViewBinding(view: View) = FragmentMainBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabLayout()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.searchBox.setOnClickListener {
            navigate(R.id.mainFragment, R.id.action_mainFragment_to_searchFragment)
        }
        binding.tvPs.setOnClickListener {
            navigate(R.id.mainFragment, R.id.action_mainFragment_to_newsPolicyFragment)
        }
    }

    private fun setUpTabLayout() {
        val title = arrayOf("Stocks", "Favourites")
        binding.apply {
            vpStocks.adapter = StocksPagerAdapter(this@MainFragment)
            TabLayoutMediator(tabLayout, vpStocks) { tab, position ->
                tab.text = title[position]
            }.attach()
        }
    }


}
