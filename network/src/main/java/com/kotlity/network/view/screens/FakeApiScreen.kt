package com.kotlity.network.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kotlity.network.R
import com.kotlity.network.view.composables.specific.sections.FakeApiAlbumPhotosSection
import com.kotlity.network.view.composables.specific.sections.FakeApiDeletePostByIdSection
import com.kotlity.network.view.composables.specific.sections.FakeApiNewPostSection
import com.kotlity.network.view.composables.specific.sections.FakeApiPostByIdSection
import com.kotlity.network.view.composables.specific.sections.FakeApiPostCommentsSection
import com.kotlity.network.view.composables.specific.sections.FakeApiPostsSection
import com.kotlity.network.view.composables.specific.sections.FakeApiUpdatePostSection
import com.kotlity.network.viewmodel.FakeApiViewModel

@Composable
fun FakeApiScreen(
    fakeApiViewModel: FakeApiViewModel = hiltViewModel()
) {
    val verticalScrollState = rememberScrollState()

    val fakeApiState by fakeApiViewModel.fakeApiStateFlow.collectAsStateWithLifecycle()
    val postsState = fakeApiState.postsState
    val postByIdState = fakeApiState.postByIdState
    val postCommentsState = fakeApiState.postCommentsState
    val albumPhotosState = fakeApiState.albumPhotosState
    val newPostState = fakeApiState.newPostState
    val updatedPostState = fakeApiState.updatedPostState
    val deletePostByIdState = fakeApiState.deletedPostByIdState

    Surface(
       modifier = Modifier
           .fillMaxSize()
           .verticalScroll(verticalScrollState)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = dimensionResource(R.dimen._10dp))
            ) {
                FakeApiPostsSection(
                    postsState = postsState,
                    getAllPostsButtonClick = {
                        fakeApiViewModel.getAllPosts()
                    }
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen._10dp)))
                FakeApiPostByIdSection(
                    postByIdState = postByIdState,
                    onExpandedChange = fakeApiViewModel::isPostByIdExposedDropdownMenuExpandedUpdate,
                    onDismissDropdownMenuRequest = {
                        fakeApiViewModel.isPostByIdExposedDropdownMenuExpandedUpdate(isExpanded = false)
                    },
                    onPostByIdItemClicked = { id ->
                        fakeApiViewModel.apply {
                            postByIdUpdate(id = id)
                            isPostByIdExposedDropdownMenuExpandedUpdate(isExpanded = false)
                        }
                    },
                    getPostByIdButtonClick = {
                        fakeApiViewModel.getPostById(id = postByIdState.dropdownMenuState.id)
                    }
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen._10dp)))
                FakeApiPostCommentsSection(
                    postCommentsState = postCommentsState,
                    onExpandedChange = fakeApiViewModel::isPostCommentsExposedDropdownMenuExpandedUpdate
                    ,
                    onDismissDropdownMenuRequest = {
                        fakeApiViewModel.isPostCommentsExposedDropdownMenuExpandedUpdate(isExpanded = false)
                    },
                    onIdItemClicked = { id ->
                        fakeApiViewModel.apply {
                            postCommentsIdUpdate(id = id)
                            isPostCommentsExposedDropdownMenuExpandedUpdate(isExpanded = false)
                        }
                    },
                    getPostCommentsByIdButtonClick = {
                        fakeApiViewModel.getPostCommentsByPostId(postId = postCommentsState.dropdownMenuState.id)
                    }
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen._10dp)))
                FakeApiAlbumPhotosSection(
                    albumPhotosState = albumPhotosState,
                    onExpandedChange = fakeApiViewModel::isAlbumPhotosExposedDropdownMenuExpandedUpdate
                    ,
                    onDismissDropdownMenuRequest = {
                        fakeApiViewModel.isAlbumPhotosExposedDropdownMenuExpandedUpdate(isExpanded = false)
                    },
                    onAlbumIdItemClicked = { albumId ->
                        fakeApiViewModel.apply {
                            albumPhotosIdUpdate(albumId = albumId)
                            isAlbumPhotosExposedDropdownMenuExpandedUpdate(isExpanded = false)
                        }
                    },
                    getAlbumPhotosByAlbumIdButtonClick = {
                        fakeApiViewModel.getAlbumPhotosByAlbumId(albumId = albumPhotosState.dropDownMenuState.id)
                    }
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen._10dp)))
                FakeApiNewPostSection(
                    newPostState = newPostState,
                    onUserIdChange = { userId ->
                        if (userId.isNotBlank()) fakeApiViewModel.updatePostUserId(userId = userId.toInt())
                    },
                    onUserIdClearIconClick = {
                        fakeApiViewModel.updatePostUserId(userId = 1)
                    },
                    onTitleChange = fakeApiViewModel::updatePostTitle,
                    onTitleClearIconClick = {
                        fakeApiViewModel.updatePostTitle(title = "")
                    },
                    onBodyChange = fakeApiViewModel::updatePostBody,
                    onBodyClearIconClick = {
                        fakeApiViewModel.updatePostBody(body = "")
                    },
                    onCreateNewPostButtonClick = {
                        fakeApiViewModel.createNewPost(newPost = newPostState.defaultEditablePost.post)
                    }
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen._10dp)))
                FakeApiUpdatePostSection(
                    updatedPostState = updatedPostState,
                    onUserIdChange = { userId ->
                        if (userId.isNotBlank()) fakeApiViewModel.updatePostUserId(userId = userId.toInt(), isForNewPostState = false)
                    },
                    onUserIdClearIconClick = {
                        fakeApiViewModel.updatePostUserId(userId = 1, isForNewPostState = false)
                    },
                    onIdChange = { id ->
                        if (id.isNotBlank()) fakeApiViewModel.updatePostId(id = id.toInt())
                    },
                    onIdClearIconClick = {
                        fakeApiViewModel.updatePostId(id = 1)
                    },
                    onTitleChange = { updatedTitle ->
                        fakeApiViewModel.updatePostTitle(title = updatedTitle, isForNewPostState = false)
                    },
                    onTitleClearIconClick = {
                        fakeApiViewModel.updatePostTitle(title = "", isForNewPostState = false)
                    },
                    onBodyChange = { updatedBody ->
                        fakeApiViewModel.updatePostBody(body = updatedBody, isForNewPostState = false)
                    },
                    onBodyClearIconClick = {
                        fakeApiViewModel.updatePostBody(body = "", isForNewPostState = false)
                    },
                    onUpdatePostButtonClick = {
                        fakeApiViewModel.updatePostById(
                            id = updatedPostState.defaultEditablePost.post.id,
                            updatedPost = updatedPostState.defaultEditablePost.post
                        )
                    }
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen._10dp)))
                FakeApiDeletePostByIdSection(
                    deletedPostByIdState = deletePostByIdState,
                    onExpandedChange = fakeApiViewModel::isDeletePostByIdExposedDropdownMenuExpandedUpdate,
                    onDismissDropdownMenuRequest = {
                        fakeApiViewModel.isDeletePostByIdExposedDropdownMenuExpandedUpdate(isExpanded = false)
                    },
                    onIdItemClicked = { id ->
                        fakeApiViewModel.apply {
                            updateId(id = id)
                            isDeletePostByIdExposedDropdownMenuExpandedUpdate(isExpanded = false)
                        }
                    },
                    deletePostByIdButtonClick = {
                        fakeApiViewModel.deletePostById(id = deletePostByIdState.dropdownMenuState.id)
                    }
                )
            }
        }
    }
}