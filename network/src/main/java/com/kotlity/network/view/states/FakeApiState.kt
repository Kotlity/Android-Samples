package com.kotlity.network.view.states

import android.os.Parcelable
import com.kotlity.network.view.states.embedded.AlbumPhotosState
import com.kotlity.network.view.states.embedded.DeletedPostByIdState
import com.kotlity.network.view.states.embedded.NewPostState
import com.kotlity.network.view.states.embedded.PostByIdState
import com.kotlity.network.view.states.embedded.PostCommentsState
import com.kotlity.network.view.states.embedded.PostsState
import com.kotlity.network.view.states.embedded.UpdatedPostState
import kotlinx.parcelize.Parcelize

@Parcelize
data class FakeApiState(
    val postsState: PostsState = PostsState(),
    val postByIdState: PostByIdState = PostByIdState(),
    val postCommentsState: PostCommentsState = PostCommentsState(),
    val albumPhotosState: AlbumPhotosState = AlbumPhotosState(),
    val newPostState: NewPostState = NewPostState(),
    val updatedPostState: UpdatedPostState = UpdatedPostState(),
    val deletedPostByIdState: DeletedPostByIdState = DeletedPostByIdState()
): Parcelable