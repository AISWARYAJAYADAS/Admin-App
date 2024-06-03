package com.example.adminapp.model.user

data class GetUsersResponseItem(
    val account_creation_date: String,
    val address: String,
    val approved: Int,
    val blocked: Int,
    val email: String,
    val id: Int,
    val level: Int,
    val name: String,
    val password: String,
    val phone: String,
    val pincode: String,
    val user_id: String
)