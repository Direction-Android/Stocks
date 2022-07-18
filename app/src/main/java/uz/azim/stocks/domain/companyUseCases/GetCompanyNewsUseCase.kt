package uz.azim.stocks.domain.companyUseCases

import kotlinx.coroutines.flow.Flow
import uz.azim.stocks.data.repo.company.CompanyRepo
import uz.azim.stocks.model.News
import uz.azim.stocks.util.Resource

class GetCompanyNewsUseCase(private val companyRepo: CompanyRepo) {

    fun execute(
        symbol: String,
        fromDate: String,
        toDate: String,
    ): Flow<Resource<List<News>>> = companyRepo.companyNews(symbol, fromDate, toDate)
}