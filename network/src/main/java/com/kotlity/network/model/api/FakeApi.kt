package com.kotlity.network.model.api

import com.kotlity.network.model.dtos.AlbumPhotoDto
import com.kotlity.network.model.dtos.PostCommentDto
import com.kotlity.network.model.dtos.PostDto
import com.kotlity.network.utils.Constants.ALBUMS_ROUTE
import com.kotlity.network.utils.Constants.COMMENTS_ROUTE
import com.kotlity.network.utils.Constants.POSTS_ROUTE
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface FakeApi {

    @GET(POSTS_ROUTE)
    suspend fun getAllPosts(): List<PostDto>?

    @GET("$POSTS_ROUTE/{id}")
    suspend fun getPostById(@Path("id") id: Int): PostDto?

    @GET(COMMENTS_ROUTE)
    suspend fun getPostCommentsByPostId(@Query("postId") postId: Int): List<PostCommentDto>?

    @GET("$ALBUMS_ROUTE/{albumId}/photos")
    suspend fun getAlbumPhotosByAlbumId(@Path("albumId") albumId: Int): List<AlbumPhotoDto>?

    @POST(POSTS_ROUTE)
    suspend fun createNewPost(@Body newPost: PostDto): PostDto?

    @PUT("$POSTS_ROUTE/{id}")
    suspend fun updatePostById(@Path("id") id: Int, @Body updatedPost: PostDto): PostDto?

    @DELETE("$POSTS_ROUTE/{id}")
    suspend fun deletePostById(@Path("id") id: Int)
}