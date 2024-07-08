package com.kotlity.network.view.composables.generic

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T> FakeApiAnimatedContent(
    modifier: Modifier = Modifier,
    targetState: T,
    content: @Composable (T) -> Unit,
    label: String = "",
    transitionSpec: AnimatedContentTransitionScope<T>.() -> ContentTransform = {
        (fadeIn(animationSpec = tween(220, delayMillis = 90)) +
         scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90)))
         .togetherWith(fadeOut(animationSpec = tween(90)))
    },
    contentAlignment: Alignment = Alignment.TopStart,
    contentKey: (T) -> Any? = { it }
) {
    AnimatedContent(
        modifier = modifier,
        targetState = targetState,
        label = label,
        transitionSpec = transitionSpec,
        contentAlignment = contentAlignment,
        contentKey = contentKey
    ) {
        content(it)
    }
}