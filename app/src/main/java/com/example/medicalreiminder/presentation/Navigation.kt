package com.example.medicalreiminder.presentation

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medicalreiminder.model.utils.navigateAndDontComeBack
import com.example.medicalreiminder.viewModels.AuthenticationViewModel
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

    NavHost(navController = navController, startDestination = Main) {
        val authenticationViewModel = AuthenticationViewModel()
        val reminderViewModel = ReminderViewModel(appContext)
        composable<Authentication> {

            val viewModel = ReminderViewModel(appContext)

            auth(modifier, viewModel) {
                navController.navigate(route = SignIn)
            }
        }
        composable<SignIn> {
            LoginPage(modifier, authenticationViewModel, {
                navController.navigateAndDontComeBack(Main)
            }) {
                navController.navigate(route = SignUp)
            }
        }
        composable<SignUp> {
            SignupScreen(modifier,authenticationViewModel, onSignUp = {
                navController.navigate(route = Authentication)
            }){
                navController.navigate(route = SignIn)
            }
        }
        composable<Main> {
            MainScreen(viewModel = reminderViewModel, modifier = modifier, onAddMed = {reminder ->
                navController.navigate(route = Main)
            }){

            }
        }

    }

}

@Composable
fun auth(modifier: Modifier = Modifier, viewModel: ReminderViewModel, nav: () -> Unit) {
    Column {
        //Text("product${viewModel.prodName}", modifier = modifier)
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