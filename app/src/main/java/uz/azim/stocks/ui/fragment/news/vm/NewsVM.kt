package uz.azim.stocks.ui.fragment.news.vm
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.companyUseCases.GetCompanyNewsUseCase
import uz.azim.stocks.ui.BaseViewModel

class NewsVM(private val getCompanyNewsUseCase: GetCompanyNewsUseCase) : BaseViewModel() {

    fun companyNews(
        symbol: String,
        fromDate: String,
        currentDate: String,
    ) = getCompanyNewsUseCase.execute(symbol, fromDate, currentDate)
}
