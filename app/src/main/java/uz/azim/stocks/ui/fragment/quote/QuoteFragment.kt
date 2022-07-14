package uz.azim.stocks.ui.fragment.quote

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import uz.azim.stocks.R
import uz.azim.stocks.databinding.FragmentQuoteBinding
import uz.azim.stocks.ui.fragment.BaseFragment
import uz.azim.stocks.ui.fragment.chart.ChartFragment
import uz.azim.stocks.ui.fragment.news.NewsFragment
import uz.azim.stocks.ui.fragment.quote.adapter.QuoteDetailsAdapter

const val SYMBOL = "Symbol"

class QuoteFragment : BaseFragment<FragmentQuoteBinding>(R.layout.fragment_quote) {
    private val args by navArgs<QuoteFragmentArgs>()

    override fun initViewBinding(view: View) = FragmentQuoteBinding.bind(view)

    private val tabTitles = arrayListOf("Chart", "News")
    private val fragments = arrayListOf<Fragment>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareFragments()
        setUpViews()
        setUpVpTab()
    }

    private fun prepareFragments() {
        val chart = ChartFragment()
        val news = NewsFragment()
        val bundle = Bundle()
        bundle.putString(SYMBOL, args.symbol)
        chart.arguments = bundle
        news.arguments = bundle
        fragments.add(chart)
        fragments.add(news)
    }

    private fun setUpViews() {
        binding.tvTitle.text = args.symbol
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpVpTab() {
        binding.apply {
            vpQuote.adapter = QuoteDetailsAdapter(this@QuoteFragment as Fragment, fragments)
            TabLayoutMediator(tabLayout, vpQuote) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }

}
