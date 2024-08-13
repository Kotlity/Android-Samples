package com.kotlity.websockets.utils

import com.kotlity.websockets.utils.responses.NetworkFailure
import com.kotlity.websockets.utils.responses.HandleStatus
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.MalformedURLException
import java.net.NoRouteToHostException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLException
import kotlin.coroutines.CoroutineContext

inline fun <reified R, reified MR> HttpClient.executeKtorObservableCall(
    crossinline httpRequestBuilder: HttpRequestBuilder.() -> Unit,
    crossinline mapper: (R) -> MR,
    coroutineContext: CoroutineContext = Dispatchers.IO
): Flow<HandleStatus<MR, NetworkFailure>> {
    return flow<HandleStatus<MR, NetworkFailure>> {
        try {
            emit(HandleStatus.Undefined())
            val builder = HttpRequestBuilder()
            builder.httpRequestBuilder()
            val ktorCallHttpResponse = request(builder = builder)
            if (ktorCallHttpResponse.status == HttpStatusCode.NoContent) emit(HandleStatus.Error(error = NetworkFailure.NO_DATA_RECEIVED))
            else {
                val ktorCallResponse = ktorCallHttpResponse.body<R>()
                val mappedResult = mapper(ktorCallResponse)
                emit(HandleStatus.Success(data = mappedResult))
            }
        } catch (e: ConnectException) {
            emit(HandleStatus.Error(error = NetworkFailure.CONNECT_EXCEPTION))
        } catch (e: NoRouteToHostException) {
            emit(HandleStatus.Error(error = NetworkFailure.NO_ROUTE_TO_HOST_EXCEPTION))
        } catch (e: MalformedURLException) {
            emit(HandleStatus.Error(error = NetworkFailure.MALFORMED_URL_EXCEPTION))
        } catch (e: SSLException) {
            emit(HandleStatus.Error(error = NetworkFailure.SSL_EXCEPTION))
        } catch (e: TimeoutException) {
            emit(HandleStatus.Error(error = NetworkFailure.TIMEOUT_EXCEPTION))
        }
    }.flowOn(coroutineContext)
}

suspend inline fun <reified R, reified MR> HttpClient.executeKtorCall(
    crossinline httpRequestBuilder: HttpRequestBuilder.() -> Unit,
    noinline mapper: ((R) -> MR)? = null,
    coroutineContext: CoroutineContext = Dispatchers.IO
): HandleStatus<MR, NetworkFailure> {
    return withContext(coroutineContext) {
        try {
            val builder = HttpRequestBuilder()
            builder.httpRequestBuilder()
            val ktorCallHttpResponse = request(builder = builder)
            val ktorCallResponse = ktorCallHttpResponse.body<R>()
            if (ktorCallHttpResponse.status == HttpStatusCode.NoContent) HandleStatus.Success(data = ktorCallResponse as MR)
            else {
                val mappedResult = mapper?.invoke(ktorCallResponse) ?: ktorCallResponse as MR
                HandleStatus.Success(data = mappedResult)
            }
        } catch (e: ConnectException) {
            HandleStatus.Error(error = NetworkFailure.CONNECT_EXCEPTION)
        } catch (e: NoRouteToHostException) {
            HandleStatus.Error(error = NetworkFailure.NO_ROUTE_TO_HOST_EXCEPTION)
        } catch (e: MalformedURLException) {
            HandleStatus.Error(error = NetworkFailure.MALFORMED_URL_EXCEPTION)
        } catch (e: SSLException) {
            HandleStatus.Error(error = NetworkFailure.SSL_EXCEPTION)
        } catch (e: TimeoutException) {
            HandleStatus.Error(error = NetworkFailure.TIMEOUT_EXCEPTION)
        }
    }
}