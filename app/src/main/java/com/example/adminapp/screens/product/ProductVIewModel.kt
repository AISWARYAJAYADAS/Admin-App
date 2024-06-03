package com.example.adminapp.screens.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminapp.api.ApiService
import com.example.adminapp.model.product.CreateProductRequest
import com.example.adminapp.model.product.CreateProductResponse
import com.example.adminapp.model.product.GetProductsResponseItem
import com.example.adminapp.screens.user.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _productCreationState = MutableStateFlow<NetworkState<CreateProductResponse>?>(null)
    val productCreationState: StateFlow<NetworkState<CreateProductResponse>?> = _productCreationState

    private val _productsResponseState = MutableStateFlow<NetworkState<List<GetProductsResponseItem>>>(NetworkState.Idle)
    val productsResponseState: StateFlow<NetworkState<List<GetProductsResponseItem>>> = _productsResponseState

    fun createProduct(product: CreateProductRequest) {
        viewModelScope.launch {
            _productCreationState.value = NetworkState.Loading
            try {
                val response = apiService.createProduct(product)
                _productCreationState.value = NetworkState.Success(response)
            } catch (e: Exception) {
                _productCreationState.value = NetworkState.Error(e.message)
            }
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _productsResponseState.value = NetworkState.Loading
            try {
                val response = apiService.getProducts()
                _productsResponseState.value = NetworkState.Success(response)
            } catch (e: Exception) {
                _productsResponseState.value = NetworkState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}
