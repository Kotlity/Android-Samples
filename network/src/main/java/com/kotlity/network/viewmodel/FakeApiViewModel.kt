package com.kotlity.network.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlity.network.model.data.Post
import com.kotlity.network.view.states.embedded.data.PostCommentsData
import com.kotlity.network.model.repositories.FakeApiRepository
import com.kotlity.network.utils.FAKE_API_STATE_KEY
import com.kotlity.network.utils.handlers.handleResponse
import com.kotlity.network.utils.updateState
import com.kotlity.network.utils.updatePostValue
import com.kotlity.network.view.states.DefaultEditablePost
import com.kotlity.network.view.states.FakeApiState
import com.kotlity.network.view.states.embedded.PostsState
import com.kotlity.network.view.states.embedded.data.AlbumPhotosData
import com.kotlity.network.view.states.embedded.data.DeletePostData
import com.kotlity.network.view.states.embedded.data.PostData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FakeApiViewModel @Inject constructor(
    private val fakeApiRepository: FakeApiRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val fakeApiStateFlow = savedStateHandle.getStateFlow(FAKE_API_STATE_KEY, FakeApiState())

    fun getAllPosts() {
        fakeApiRepository.getAllPosts()
            .onEach {
                it.handleResponse(
                    onLoading = {
                        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                            fakeApiState.copy(postsState = PostsState(arePostsLoading = true))
                        }
                    },
                    onSuccess = { posts ->
                        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                            fakeApiState.copy(
                                postsState = fakeApiState.postsState.copy(
                                    arePostsLoading = false,
                                    posts = posts!!,
                                    uiText = null
                                )
                            )
                        }
                    },
                    onError = { uiText ->
                        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                            fakeApiState.copy(
                                postsState = fakeApiState.postsState.copy(
                                    arePostsLoading = false,
                                    posts = emptyList(),
                                    uiText = uiText
                                )
                            )
                        }
                    }
                )
            }
            .launchIn(viewModelScope)
    }

    fun isPostByIdExposedDropdownMenuExpandedUpdate(isExpanded: Boolean) {
        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
            fakeApiState.copy(postByIdState = fakeApiState.postByIdState.copy(dropdownMenuState = fakeApiState.postByIdState.dropdownMenuState.copy(isDropdownMenuExpanded = isExpanded)))
        }
    }

    fun postByIdUpdate(id: Int) {
        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
            fakeApiState.copy(postByIdState = fakeApiState.postByIdState.copy(dropdownMenuState = fakeApiState.postByIdState.dropdownMenuState.copy(id = id)))
        }
    }

    fun getPostById(id: Int) {
        viewModelScope.launch {
            fakeApiRepository.getPostById(id).handleResponse(
                onSuccess = { post ->
                    savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                        fakeApiState.copy(postByIdState = fakeApiState.postByIdState.copy(postData = fakeApiState.postByIdState.postData.copy(post = post, uiText = null)))
                    }
                },
                onError = { uiText ->
                    savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                        fakeApiState.copy(postByIdState = fakeApiState.postByIdState.copy(postData = fakeApiState.postByIdState.postData.copy(post = null, uiText = uiText)))
                    }
                }
            )
        }
    }

    fun isPostCommentsExposedDropdownMenuExpandedUpdate(isExpanded: Boolean) {
        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
            fakeApiState.copy(postCommentsState = fakeApiState.postCommentsState.copy(dropdownMenuState = fakeApiState.postCommentsState.dropdownMenuState.copy(isDropdownMenuExpanded = isExpanded)))
        }
    }

    fun postCommentsIdUpdate(id: Int) {
        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
            fakeApiState.copy(postCommentsState = fakeApiState.postCommentsState.copy(dropdownMenuState = fakeApiState.postCommentsState.dropdownMenuState.copy(id = id)))
        }
    }

    fun getPostCommentsByPostId(postId: Int) {
        fakeApiRepository.getPostCommentsByPostId(postId = postId)
            .onEach {
                it.handleResponse(
                    onLoading = {
                        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                            fakeApiState.copy(postCommentsState = fakeApiState.postCommentsState.copy(postCommentsData = PostCommentsData(arePostCommentsLoading = true)))
                        }
                    },
                    onSuccess = { postComments ->
                        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                            fakeApiState.copy(postCommentsState = fakeApiState.postCommentsState.copy(postCommentsData = PostCommentsData(postComments = postComments!!)))
                        }
                    },
                    onError = { uiText ->
                        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                            fakeApiState.copy(postCommentsState = fakeApiState.postCommentsState.copy(postCommentsData = PostCommentsData(uiText = uiText)))
                        }
                    }
                )
            }
            .launchIn(viewModelScope)
    }

    fun isAlbumPhotosExposedDropdownMenuExpandedUpdate(isExpanded: Boolean) {
        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
            fakeApiState.copy(albumPhotosState = fakeApiState.albumPhotosState.copy(dropDownMenuState = fakeApiState.albumPhotosState.dropDownMenuState.copy(isDropdownMenuExpanded = isExpanded)))
        }
    }

    fun albumPhotosIdUpdate(albumId: Int) {
        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
            fakeApiState.copy(albumPhotosState = fakeApiState.albumPhotosState.copy(dropDownMenuState = fakeApiState.albumPhotosState.dropDownMenuState.copy(id = albumId)))
        }
    }

    fun getAlbumPhotosByAlbumId(albumId: Int) {
        fakeApiRepository.getAlbumPhotosByAlbumId(albumId = albumId)
            .onEach {
                it.handleResponse(
                    onLoading = {
                        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                            fakeApiState.copy(albumPhotosState = fakeApiState.albumPhotosState.copy(albumPhotosData = AlbumPhotosData(areAlbumPhotosLoading = true)))
                        }
                    },
                    onSuccess = { albumPhotos ->
                        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                            fakeApiState.copy(albumPhotosState = fakeApiState.albumPhotosState.copy(albumPhotosData = AlbumPhotosData(albumPhotos = albumPhotos!!)))
                        }
                    },
                    onError = { uiText ->
                        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                            fakeApiState.copy(albumPhotosState = fakeApiState.albumPhotosState.copy(albumPhotosData = AlbumPhotosData(uiText = uiText)))
                        }
                    }
                )
            }
            .launchIn(viewModelScope)
    }

    fun updatePostUserId(userId: Int, isForNewPostState: Boolean = true) {
        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
            if (isForNewPostState) {
                fakeApiState.copy(newPostState = fakeApiState.newPostState.copy(
                    defaultEditablePost = fakeApiState.newPostState.defaultEditablePost.updatePostValue(value = userId))
                )
            } else {
                fakeApiState.copy(updatedPostState = fakeApiState.updatedPostState.copy(
                    defaultEditablePost = fakeApiState.updatedPostState.defaultEditablePost.updatePostValue(value = userId))
                )
            }
        }
    }

    fun updatePostId(id: Int) {
        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
            fakeApiState.copy(updatedPostState = fakeApiState.updatedPostState.copy(
                defaultEditablePost = fakeApiState.updatedPostState.defaultEditablePost.updatePostValue(value = id, isUpdateUserId = false))
            )
        }
    }

    fun updatePostTitle(title: String, isForNewPostState: Boolean = true) {
        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
            if (isForNewPostState) {
                fakeApiState.copy(newPostState = fakeApiState.newPostState.copy(
                    defaultEditablePost = fakeApiState.newPostState.defaultEditablePost.updatePostValue(value = title))
                )
            } else {
                fakeApiState.copy(updatedPostState = fakeApiState.updatedPostState.copy(
                    defaultEditablePost = fakeApiState.updatedPostState.defaultEditablePost.updatePostValue(value = title))
                )
            }
        }
    }

    fun updatePostBody(body: String, isForNewPostState: Boolean = true) {
        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
            if (isForNewPostState) {
                fakeApiState.copy(newPostState = fakeApiState.newPostState.copy(
                    defaultEditablePost = fakeApiState.newPostState.defaultEditablePost.updatePostValue(value = body, isUpdateTitle = false))
                )
            } else {
                fakeApiState.copy(updatedPostState = fakeApiState.updatedPostState.copy(
                    defaultEditablePost = fakeApiState.updatedPostState.defaultEditablePost.updatePostValue(value = body, isUpdateTitle = false))
                )
            }
        }
    }

    fun createNewPost(newPost: Post) {
        viewModelScope.launch {
            fakeApiRepository.createNewPost(newPost = newPost).handleResponse(
                onSuccess = { newPost ->
                    savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                        fakeApiState.copy(newPostState = fakeApiState.newPostState.copy(defaultEditablePost = DefaultEditablePost(), newPostData = PostData(post = newPost)))
                    }
                },
                onError = { uiText ->
                    savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                        fakeApiState.copy(newPostState = fakeApiState.newPostState.copy(newPostData = PostData(uiText = uiText)))
                    }
                }
            )
        }
    }

    fun updatePostById(id: Int, updatedPost: Post) {
        viewModelScope.launch {
            fakeApiRepository.updatePostById(id = id, updatedPost = updatedPost).handleResponse(
                onSuccess = { updatedPost ->
                    savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                        fakeApiState.copy(updatedPostState = fakeApiState.updatedPostState.copy(defaultEditablePost = DefaultEditablePost(), postData = PostData(post = updatedPost)))
                    }
                },
                onError = { uiText ->
                    savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                        fakeApiState.copy(updatedPostState = fakeApiState.updatedPostState.copy(postData = PostData(uiText = uiText)))
                    }
                }
            )
        }
    }

    fun isDeletePostByIdExposedDropdownMenuExpandedUpdate(isExpanded: Boolean) {
        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
            fakeApiState.copy(deletedPostByIdState = fakeApiState.deletedPostByIdState.copy(dropdownMenuState = fakeApiState.deletedPostByIdState.dropdownMenuState.copy(isDropdownMenuExpanded = isExpanded)))
        }
    }

    fun updateId(id: Int) {
        savedStateHandle.updateState<FakeApiState> { fakeApiState ->
            fakeApiState.copy(deletedPostByIdState = fakeApiState.deletedPostByIdState.copy(dropdownMenuState = fakeApiState.deletedPostByIdState.dropdownMenuState.copy(id = id)))
        }
    }

    fun deletePostById(id: Int) {
        viewModelScope.launch {
            fakeApiRepository.deletePostById(id = id).handleResponse(
                onSuccess = {
                    savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                        fakeApiState.copy(deletedPostByIdState = fakeApiState.deletedPostByIdState.copy(deletePostData = DeletePostData(isPostSuccessfullyDeleted = true)))
                    }
                },
                onError = { uiText ->
                    savedStateHandle.updateState<FakeApiState> { fakeApiState ->
                        fakeApiState.copy(deletedPostByIdState = fakeApiState.deletedPostByIdState.copy(deletePostData = DeletePostData(uiText = uiText)))
                    }
                }
            )
        }
    }
}