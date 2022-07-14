package uz.azim.stocks.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import uz.azim.stocks.util.Variables

abstract class BaseFragment<T : ViewBinding>(
    @LayoutRes private val layout: Int,
) : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    private var isFirstNetworkCheck = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout, container, false)
        _binding = initViewBinding(view)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    abstract fun initViewBinding(view: View): T

    open fun subscribeObservers() {
        Variables.isNetworkConnected.observe(viewLifecycleOwner, Observer {event->
            event.getContentIfNotHandled()?.let { isNetworkAvailable->
                if (isNetworkAvailable && isFirstNetworkCheck)
                    onNetworkAvailable()
                else
                    onNetworkLost()
            }
        })
    }

    open fun onNetworkAvailable(){}

    open fun onNetworkLost(){}

    open fun beforeDestroyBinding() {}

    override fun onDestroyView() {
        beforeDestroyBinding()
        _binding = null
        super.onDestroyView()
    }

}