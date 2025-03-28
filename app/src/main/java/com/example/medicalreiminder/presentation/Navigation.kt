package com.example.medicalreiminder.presentation

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.medicalreiminder.viewModels.ReminderViewModel
import kotlinx.serialization.Serializable

@Serializable
object Authentication

@Serializable
object SignIn

@Serializable
object SignUp

@Serializable
object Main


@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    appContext: Application
) {

    NavHost(navController = navController, startDestination = Authentication) {
        composable<Authentication> {

            val viewModel = ReminderViewModel(appContext)
            auth(modifier, viewModel) {
                navController.navigate(route = SignIn)
            }
        }
        composable<SignIn> {
            signin(modifier) {
                navController.navigate(route = SignUp)
            }
        }
        composable<SignUp> {
            signup(modifier) {
                navController.navigate(route = Main)
            }
        }
        composable<Main> {
            main(modifier) {
                navController.navigate(route = Authentication)
            }
        }

    }

}

@Composable
fun auth(modifier: Modifier = Modifier, viewModel: ReminderViewModel, nav: () -> Unit) {
    Column {
        Text("product${viewModel.prodName}", modifier = modifier)
        Button(modifier = modifier, onClick = { viewModel.scanBarcode() }) {
            Text("scan")
        }
    }

}

@Composable
fun signin(modifier: Modifier = Modifier, nav: () -> Unit) {
    Button(modifier = modifier, onClick = { nav() }) {
        Text("SignIn screen")
    }
}

@Composable
fun signup(modifier: Modifier = Modifier, nav: () -> Unit) {
    Button(modifier = modifier, onClick = { nav() }) {
        Text("signUp screen")
    }
}

@Composable
fun main(modifier: Modifier = Modifier, nav: () -> Unit) {
    Button(modifier = modifier, onClick = { nav() }) {
        Text("main screen")
    }
}