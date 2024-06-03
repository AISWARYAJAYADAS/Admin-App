package com.example.adminapp.navigation

import kotlinx.serialization.Serializable


sealed class Routes {
    @Serializable
    object UserListScreen

    @Serializable
    data class UserDetailScreen(val userId: String)

    @Serializable
    object ProductAddScreen

    @Serializable
    object ProductListScreen
}