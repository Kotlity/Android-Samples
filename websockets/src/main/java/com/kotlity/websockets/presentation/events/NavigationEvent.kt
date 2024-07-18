package com.kotlity.websockets.presentation.events

sealed interface NavigationEvent<D> {
    data class OnNavigate<D>(val data: D? = null): NavigationEvent<D>
}