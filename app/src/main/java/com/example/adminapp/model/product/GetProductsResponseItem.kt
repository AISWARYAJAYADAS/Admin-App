package com.example.adminapp.model.product

data class GetProductsResponseItem(
    val category: String,
    val isActive: Int,
    val price: Double,
    val product_id: Int,
    val productname: String,
    val stock: Int
)