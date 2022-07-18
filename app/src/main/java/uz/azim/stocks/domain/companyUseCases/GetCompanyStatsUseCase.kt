package uz.azim.stocks.domain.companyUseCases

import kotlinx.coroutines.flow.Flow
import uz.azim.stocks.data.repo.company.CompanyRepo
import uz.azim.stocks.model.StockStat
import uz.azim.stocks.util.Resource

class GetCompanyStatsUseCase(private val companyRepo: CompanyRepo) {

    fun execute(symbol: String):Flow<Resource<List<StockStat>>> = companyRepo.getCompanyStats(symbol)
}