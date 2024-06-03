package com.example.adminapp.api

import com.example.adminapp.model.product.CreateProductRequest
import com.example.adminapp.model.product.CreateProductResponse
import com.example.adminapp.model.product.GetProductsResponse
import com.example.adminapp.model.product.GetProductsResponseItem
import com.example.adminapp.model.user.GetUsersResponse
import com.example.adminapp.model.user.GetUsersResponseItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    companion object {
      const val BASE_URL = "https://ajzzz.pythonanywhere.com"
    }
    @GET("/users")
    suspend fun getUsers(): GetUsersResponse

    @GET("/users/{userId}")
    suspend fun getUserDetails(@Path("userId") userId: String): GetUsersResponseItem

    @POST("/create_product")
    suspend fun createProduct(@Body product: CreateProductRequest): CreateProductResponse

    @GET("/products")
    suspend fun getProducts(): List<GetProductsResponseItem>
}