package com.kotlity.network.utils

import androidx.lifecycle.SavedStateHandle
import com.kotlity.network.view.states.DefaultEditablePost

const val FAKE_API_STATE_KEY = "FAKE_API_STATE_KEY"

inline fun <reified T> SavedStateHandle.updateState(key: String = FAKE_API_STATE_KEY, updateBlock: (T) -> T) {
    val currentState: T? = this[key]
    currentState?.let {
        this[key] = updateBlock(it)
    }
}

inline fun <reified T> DefaultEditablePost.updatePostValue(value: T, isUpdateUserId: Boolean = true, isUpdateTitle: Boolean = true): DefaultEditablePost {
    return when(T::class) {
        Int::class -> {
            if (isUpdateUserId) copy(post = this.post.copy(userId = value as Int))
            else copy(post = this.post.copy(id = value as Int))
        }
        String::class -> {
            if (isUpdateTitle) copy(post = this.post.copy(title = value as String))
            else copy(post = this.post.copy(body = value as String))
        }
        else -> return DefaultEditablePost()
    }
}