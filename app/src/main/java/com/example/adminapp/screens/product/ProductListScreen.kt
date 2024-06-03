import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.adminapp.model.product.GetProductsResponseItem
import com.example.adminapp.screens.product.ProductViewModel
import com.example.adminapp.screens.user.NetworkState

// ProductListScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel = hiltViewModel()
) {
    val productsState by productViewModel.productsResponseState.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.fetchProducts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product List") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            ProductList(productsState = productsState)
        }
    }
}

// ProductList composable
@Composable
fun ProductList(productsState: NetworkState<List<GetProductsResponseItem>>) {
    when (productsState) {
        is NetworkState.Loading -> {
            // Show loading indicator
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        is NetworkState.Success -> {
            val products = productsState.data
            LazyColumn {
                items(products) { product ->
                    ProductItem(product)
                }
            }
        }

        is NetworkState.Error -> {
            // Show error message
            Text(text = "Error: ${productsState.message}", color = Color.Red)
        }

        else -> {
            // Show idle state
            Text(text = "No products available")
        }
    }
}

// ProductItem composable
@Composable
fun ProductItem(product: GetProductsResponseItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp),
        //elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = product.productname,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            Text(
                text = "Price: ${product.price}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Category: ${product.category}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Stock: ${product.stock}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = if (product.isActive == 1) "Status: Active" else "Status: Inactive",
                color = if (product.isActive == 1) Color.Green else Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}