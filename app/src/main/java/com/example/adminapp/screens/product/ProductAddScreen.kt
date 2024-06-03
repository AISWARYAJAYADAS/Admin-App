package com.example.adminapp.screens.product

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.adminapp.model.product.CreateProductRequest
import com.example.adminapp.navigation.Routes
import com.example.adminapp.screens.user.NetworkState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddScreen(navHostController: NavHostController) {
    val viewModel = hiltViewModel<ProductViewModel>()
    var productName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(true) }

    val productCreationState by viewModel.productCreationState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Product", style = MaterialTheme.typography.headlineMedium)
                },
                actions = {
                    IconButton(onClick = {
                        navHostController.navigate(Routes.ProductListScreen)
                    }) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Save")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = price,
                onValueChange = { price = it },
                label = { Text("Price") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Is Active", style = MaterialTheme.typography.bodyMedium)
                Switch(checked = isActive, onCheckedChange = { isActive = it })
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (productName.isNotBlank() && price.isNotBlank() && category.isNotBlank() && stock.isNotBlank()) {
                        val product = CreateProductRequest(
                            category = category,
                            isActive = isActive,
                            productname = productName,
                            price = price,
                            stock = stock
                        )
                        viewModel.createProduct(product)
                    } else {
                        // Show an error message indicating that all fields are required
                        // You can use a Snackbar, Toast, or any other method to display the message
                        coroutineScope.launch {
                            Toast.makeText(
                                context,
                                "Please fill in all the fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Product")
            }

            when (productCreationState) {
                is NetworkState.Loading -> CircularProgressIndicator()
                is NetworkState.Success -> {

                    LaunchedEffect(Unit) {
                        navHostController.navigate(Routes.ProductListScreen)
                    }
                }

                is NetworkState.Error -> {
                    val error = (productCreationState as NetworkState.Error).message
                    Text(text = error ?: "Unknown Error", color = MaterialTheme.colorScheme.error)
                }

                else -> {}
            }
        }
    }
}
