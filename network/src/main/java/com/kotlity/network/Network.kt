package com.kotlity.network

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.Executors

private const val ALL_POSTS = "https://jsonplaceholder.typicode.com/posts"
private const val GET_SPECIFIC_POST = "https://jsonplaceholder.typicode.com/posts/"

private data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

private const val contentType = "application/json; charset=utf-8"

fun main() {

    val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val activeThreadsCount = Thread.activeCount()
    val executorService = Executors.newFixedThreadPool(activeThreadsCount)
    val dispatcher = Dispatcher(executorService)
    val headerInterceptor = Interceptor { chain ->
        val currentRequest = chain.request()
        val modifierRequest = currentRequest.newBuilder()
            .addHeader("Content-type", contentType)
            .build()
        chain.proceed(modifierRequest)
    }
    val okHttpClient = OkHttpClient.Builder()
        .dispatcher(dispatcher)
        .addInterceptor(headerInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    val gson = Gson()
    val postRequest = Post(
        userId = 1,
        id = 101,
        title = "Post title",
        body = "This is a post body"
    )
    val requestBody = gson.toJson(postRequest, Post::class.java).toRequestBody()

    val request = Request.Builder()
        .url("$ALL_POSTS/5")
        .delete()
//        .url(ALL_POSTS)
//        .post(requestBody)
        .build()

    val callback = object: Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("onFailure: ${e.message.toString()}")
        }

        override fun onResponse(call: Call, response: Response) {
            println("onResponse: result code - ${response.code}\nheaders - ${response.headers}")
//            val responseBodyString = response.body?.string() ?: return
//            val postResponse = gson.fromJson(responseBodyString, Post::class.java)
//            println("postResponse - $postResponse")
//            val postListType = object: TypeToken<List<Post>>() {}.type
//            val postsResponse: List<Post> = gson.fromJson(responseBodyString, postListType)
//            println("postResponse - $postsResponse")
        }
    }

    try {
        val call = okHttpClient.newCall(request)
//        call.execute() // make the call synchronously by blocking the thread
        call.enqueue(callback)
    } catch (e: Exception) {
        println("an error occurred: ${e.message.toString()}")
    } finally {
        executorService.shutdown()
    }
}