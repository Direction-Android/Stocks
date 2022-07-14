package uz.azim.stocks.ui.fragment.policy

import android.view.View
import uz.azim.stocks.R
import uz.azim.stocks.databinding.FragmentNewsPolicyBinding
import uz.azim.stocks.ui.fragment.BaseFragment

class NewsPolicyFragment : BaseFragment<FragmentNewsPolicyBinding>(R.layout.fragment_news_policy) {

    override fun initViewBinding(view: View) = FragmentNewsPolicyBinding.bind(view)

}