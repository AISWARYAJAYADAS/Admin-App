package com.example.adminapp.model.product

data class CreateProductRequest(
    val category: String,
    val isActive: Boolean,
    val price: String,
    val productname: String,
    val stock: String
)