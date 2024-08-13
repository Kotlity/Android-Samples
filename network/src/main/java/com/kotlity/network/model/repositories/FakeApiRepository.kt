package com.kotlity.network.model.repositories

import com.kotlity.network.model.data.AlbumPhoto
import com.kotlity.network.model.data.Post
import com.kotlity.network.model.data.PostComment
import com.kotlity.network.utils.NetworkFailure
import com.kotlity.network.utils.Response
import kotlinx.coroutines.flow.Flow

interface FakeApiRepository {

    fun getAllPosts(): Flow<Response<List<Post>?, NetworkFailure>>

    suspend fun getPostById(id: Int): Response<Post?, NetworkFailure>

    fun getPostCommentsByPostId(postId: Int): Flow<Response<List<PostComment>?, NetworkFailure>>

    fun getAlbumPhotosByAlbumId(albumId: Int): Flow<Response<List<AlbumPhoto>?, NetworkFailure>>

    suspend fun createNewPost(newPost: Post): Response<Post?, NetworkFailure>

    suspend fun updatePostById(id: Int, updatedPost: Post): Response<Post?, NetworkFailure>

    suspend fun deletePostById(id: Int): Response<Unit, NetworkFailure>
}