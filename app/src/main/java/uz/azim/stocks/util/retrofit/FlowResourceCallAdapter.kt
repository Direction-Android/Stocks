package uz.azim.stocks.util.retrofit

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.awaitResponse
import uz.azim.stocks.util.Resource
import java.lang.reflect.Type

/**
 * To convert retrofit response to Flow<Resource<T>>.
 */
class FlowResourceCallAdapter<R>(
    private val responseType: Type,
    private val isSelfExceptionHandling: Boolean
) : CallAdapter<R, Flow<Resource<R>>> {

    override fun responseType() = responseType

    @ExperimentalCoroutinesApi
    override fun adapt(call: Call<R>): Flow<Resource<R>> = flow {

        // Firing loading resource
        emit(Resource.Loading<R>())

        val resp = call.awaitResponse()

        if (resp.isSuccessful) {
            resp.body()?.let {
                emit(Resource.Success<R>(it))
            }
        } else {
            emit(Resource.Error<R>(resp.message()))
        }

    }.catch { error ->
        if (isSelfExceptionHandling) {
            emit(Resource.Error(error.message?:"Unhandled exception"))
        } else {
            throw error
        }
    }
}