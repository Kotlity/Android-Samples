package com.kotlity.network.utils.handlers

import com.kotlity.network.utils.NetworkFailure
import com.kotlity.network.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.MalformedURLException
import java.net.NoRouteToHostException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLException

inline fun <R,MR> executeObservableRetrofitCall(
    crossinline retrofitCall: suspend () -> R?,
    crossinline mapper: (R) -> MR
): Flow<Response<MR, NetworkFailure>> {
    return flow {
        try {
            emit(Response.Loading())
            val retrofitCallResult = retrofitCall()
            if (retrofitCallResult != null) {
                val mappedResult = mapper(retrofitCallResult)
                emit(Response.Success(data = mappedResult))
            } else emit(Response.Error(errorMessage = NetworkFailure.NO_DATA_RECEIVED))
        } catch (e: ConnectException) {
            emit(Response.Error(errorMessage = NetworkFailure.CONNECT_EXCEPTION))
        } catch (e: NoRouteToHostException) {
            emit(Response.Error(errorMessage = NetworkFailure.NO_ROUTE_TO_HOST_EXCEPTION))
        } catch (e: MalformedURLException) {
            emit(Response.Error(errorMessage = NetworkFailure.MALFORMED_URL_EXCEPTION))
        } catch (e: SSLException) {
            emit(Response.Error(errorMessage = NetworkFailure.SSL_EXCEPTION))
        } catch (e: TimeoutException) {
            emit(Response.Error(errorMessage = NetworkFailure.TIMEOUT_EXCEPTION))
        } catch (e: HttpException) {
            emit(Response.Error(errorMessage = NetworkFailure.HTTP_EXCEPTION))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = NetworkFailure.IO_EXCEPTION))
        }
    }
}

suspend inline fun <R, MR> executeRetrofitCall(
    noinline retrofitCall: suspend () -> R?,
    noinline mapper: ((R) -> MR)? = null
): Response<MR, NetworkFailure> {
    return try {
        val retrofitCallResult = retrofitCall()
        if (retrofitCallResult != null && retrofitCallResult !is Unit) {
            val mappedResult = mapper?.invoke(retrofitCallResult) ?: retrofitCallResult as MR
            Response.Success(data = mappedResult)
        } else if (retrofitCallResult is Unit) Response.Success(data = retrofitCallResult as MR)
        else Response.Error(errorMessage = NetworkFailure.NO_DATA_RECEIVED)
    } catch (e: ConnectException) {
        Response.Error(errorMessage = NetworkFailure.CONNECT_EXCEPTION)
    } catch (e: NoRouteToHostException) {
        Response.Error(errorMessage = NetworkFailure.NO_ROUTE_TO_HOST_EXCEPTION)
    } catch (e: MalformedURLException) {
        Response.Error(errorMessage = NetworkFailure.MALFORMED_URL_EXCEPTION)
    } catch (e: SSLException) {
        Response.Error(errorMessage = NetworkFailure.SSL_EXCEPTION)
    } catch (e: TimeoutException) {
        Response.Error(errorMessage = NetworkFailure.TIMEOUT_EXCEPTION)
    } catch (e: HttpException) {
        Response.Error(errorMessage = NetworkFailure.HTTP_EXCEPTION)
    } catch (e: IOException) {
        Response.Error(errorMessage = NetworkFailure.IO_EXCEPTION)
    }
}