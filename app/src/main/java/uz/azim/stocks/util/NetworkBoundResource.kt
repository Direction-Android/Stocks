package uz.azim.stocks.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

/**
 * Util class to manage caching and remote request
 */
abstract class NetworkBoundResource<DB, REMOTE> {

    abstract fun fetchFromLocal(): Flow<DB>

    abstract fun fetchFromRemote(): Flow<Resource<REMOTE>>

    abstract suspend fun saveRemoteData(data: REMOTE)

    abstract fun shouldFetchFromRemote(data: DB): Boolean

    @ExperimentalCoroutinesApi
    fun asFlow() = flow<Resource<DB>> {

        val localeData = fetchFromLocal().first()

        if (shouldFetchFromRemote(localeData)) {
            log("Fetching from remote")
            fetchFromRemote()
                .collect { response ->
                    when (response) {

                        is Resource.Loading -> {
                            emit(Resource.Loading())
                        }

                        is Resource.Success -> {
                            val data = response.data!!
                            saveRemoteData(data)
                            emitLocalDbData()
                        }

                        is Resource.Error -> {
                            emit(Resource.Error(response.message.toString()))
                        }
                        is Resource.InitialState -> {}
                    }
                }
        }else{
            emitLocalDbData()
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun FlowCollector<Resource<DB>>.emitLocalDbData() {
        emit(Resource.Loading())
        log("emitLocalDB")
        emitAll(fetchFromLocal().map { dbData ->
            Resource.Success(dbData)
        })
    }
}