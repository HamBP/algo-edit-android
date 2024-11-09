package me.algosketch.algoedit.presentation.route

import kotlinx.serialization.Serializable

sealed interface MainRoute {
    @Serializable
    data object Home : MainRoute

    @Serializable
    data object Edition : MainRoute
}