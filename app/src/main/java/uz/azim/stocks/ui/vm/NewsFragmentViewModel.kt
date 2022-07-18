package uz.azim.stocks.ui.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.GetCompanyNewsUseCase

class NewsFragmentViewModel(private val getCompanyNewsUseCase: GetCompanyNewsUseCase) :
    ViewModel() {

    fun companyNews(
        symbol: String,
        fromDate: String,
        currentDate: String
    ) = flow {
        getCompanyNewsUseCase.execute(symbol, fromDate, currentDate).collect {
            emit(it)
        }
    }
}