package uz.azim.stocks.ui.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.di.RepositoryModule

class NewsFragmentViewModel : ViewModel() {
    private val companyRepo = RepositoryModule.bindCompanyRepo()

    fun companyNews(
        symbol: String,
        fromDate: String,
        currentDate: String
    ) = flow {
        companyRepo.companyNews(symbol, fromDate, currentDate).collect {
            emit(it)
        }
    }
}