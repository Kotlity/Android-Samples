package com.kotlity.network.mappers

import com.kotlity.network.model.data.AlbumPhoto
import com.kotlity.network.model.data.Post
import com.kotlity.network.model.data.PostComment
import com.kotlity.network.model.dtos.AlbumPhotoDto
import com.kotlity.network.model.dtos.PostCommentDto
import com.kotlity.network.model.dtos.PostDto

fun PostDto.toPost(): Post {
    return Post(
        userId = userId,
        id = id,
        title = title,
        body = body
    )
}

fun Post.toPostDto(): PostDto {
    return PostDto(
        userId = userId,
        id = id,
        title = title,
        body = body
    )
}

fun PostCommentDto.toPostComment(): PostComment {
    return PostComment(
        postId = postId,
        id = id,
        name = name,
        email = email,
        body = body
    )
}

fun PostComment.toPostCommentDto(): PostCommentDto {
    return PostCommentDto(
        postId = postId,
        id = id,
        name = name,
        email = email,
        body = body
    )
}

fun AlbumPhotoDto.toAlbumPhoto(): AlbumPhoto {
    return AlbumPhoto(
        albumId = albumId,
        id = id,
        title = title,
        url = url
    )
}