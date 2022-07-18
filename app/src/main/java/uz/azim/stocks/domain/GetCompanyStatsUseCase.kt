package uz.azim.stocks.domain

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.data.repo.company.CompanyRepo

class GetCompanyStatsUseCase(private val companyRepo: CompanyRepo) {

    fun execute(symbol: String) = flow{
        companyRepo.getCompanyStats(symbol).collect {
            emit(it)
        }
    }

}