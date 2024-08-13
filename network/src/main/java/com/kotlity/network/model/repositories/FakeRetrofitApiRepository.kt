package com.kotlity.network.model.repositories

import com.kotlity.network.mappers.toAlbumPhoto
import com.kotlity.network.mappers.toPost
import com.kotlity.network.mappers.toPostComment
import com.kotlity.network.mappers.toPostDto
import com.kotlity.network.model.api.FakeApi
import com.kotlity.network.model.data.AlbumPhoto
import com.kotlity.network.model.data.Post
import com.kotlity.network.model.data.PostComment
import com.kotlity.network.utils.NetworkFailure
import com.kotlity.network.utils.Response
import com.kotlity.network.utils.handlers.executeObservableRetrofitCall
import com.kotlity.network.utils.handlers.executeRetrofitCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FakeRetrofitApiRepository @Inject constructor(private val fakeApi: FakeApi): FakeApiRepository {

    override fun getAllPosts(): Flow<Response<List<Post>?, NetworkFailure>> {
        return executeObservableRetrofitCall(
            retrofitCall = {
                fakeApi.getAllPosts()
            },
            mapper = { postDtos ->
                postDtos.map { postDto ->
                    postDto.toPost()
                }
            }
        )
    }

    override suspend fun getPostById(id: Int): Response<Post?, NetworkFailure> {
        return executeRetrofitCall(
            retrofitCall = {
                fakeApi.getPostById(id = id)
            },
            mapper = { postDto ->
                postDto.toPost()
            }
        )
    }

    override fun getPostCommentsByPostId(postId: Int): Flow<Response<List<PostComment>?, NetworkFailure>> {
        return executeObservableRetrofitCall(
            retrofitCall = {
                fakeApi.getPostCommentsByPostId(postId = postId)
            },
            mapper = { postCommentDtos ->
                postCommentDtos.map { postCommentDto -> postCommentDto.toPostComment() }
            }
        )
    }

    override fun getAlbumPhotosByAlbumId(albumId: Int): Flow<Response<List<AlbumPhoto>?, NetworkFailure>> {
        return executeObservableRetrofitCall(
            retrofitCall = {
                fakeApi.getAlbumPhotosByAlbumId(albumId = albumId)
            },
            mapper = { albumPhotoDtos ->
                albumPhotoDtos.map { albumPhotoDto -> albumPhotoDto.toAlbumPhoto() }
            }
        )
    }

    override suspend fun createNewPost(newPost: Post): Response<Post?, NetworkFailure> {
        return executeRetrofitCall(
            retrofitCall = {
                fakeApi.createNewPost(newPost = newPost.toPostDto())
            },
            mapper = { postDto ->
                postDto.toPost()
            }
        )
    }

    override suspend fun updatePostById(id: Int, updatedPost: Post): Response<Post?, NetworkFailure> {
        return executeRetrofitCall(
            retrofitCall = {
                fakeApi.updatePostById(id = id, updatedPost = updatedPost.toPostDto())
            },
            mapper = { postDto ->
                postDto.toPost()
            }
        )
    }

    override suspend fun deletePostById(id: Int): Response<Unit, NetworkFailure> {
        return executeRetrofitCall(
            retrofitCall = {
                fakeApi.deletePostById(id = id)
            }
        )
    }
}