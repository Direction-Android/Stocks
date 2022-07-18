package uz.azim.stocks.domain

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.data.repo.company.CompanyRepo

class GetCompanyNewsUseCase(private val companyRepo: CompanyRepo) {

    fun execute(symbol: String, fromDate: String, currentDate: String) = flow {
        companyRepo.companyNews(symbol, fromDate, currentDate).collect {
            emit(it)
        }
    }

}