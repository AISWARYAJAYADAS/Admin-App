import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.adminapp.model.user.GetUsersResponseItem
import com.example.adminapp.screens.user.NetworkState
import com.example.adminapp.screens.user.UserViewModel

@Composable
fun UserDetailScreen(navHostController: NavHostController, userId: String) {
    val viewModel = hiltViewModel<UserViewModel>()
    val userState by viewModel.userState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.getUserDetails(userId)
    }

    userState?.let { state ->
        when (state) {
            is NetworkState.Loading -> {
                // Show loading indicator
            }

            is NetworkState.Success -> {
                val user = state.data
                DisplayUserDetails(navHostController = navHostController, user = user)
            }

            is NetworkState.Error -> {
                // Show error message
                state.message?.let { errorMessage ->
                    // Display error message
                }
            }

            NetworkState.Idle -> TODO()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayUserDetails(navHostController: NavHostController, user: GetUsersResponseItem) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.headlineMedium
                    )
                })
        },

        ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Name: ${user.name}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Phone: ${user.phone}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Address: ${user.address}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Pincode: ${user.pincode}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "User ID: ${user.user_id}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Account Created: ${user.account_creation_date}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (user.blocked == 1) "Status: Blocked" else "Status: Active",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
