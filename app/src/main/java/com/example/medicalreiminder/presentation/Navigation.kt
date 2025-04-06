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
import androidx.navigation.toRoute
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

@Serializable
data class EditReminder(
    val id: Int = 0,
    val name: String,
    val firstTime: Long,
    val secondTime: Long,
    val thirdTime: Long,
    val dose: String
)

@Serializable
data class AddReminder(
    val name: String,
    val firstTime: Long,
    val secondTime: Long,
    val thirdTime: Long,
    val dose: String
)

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    appContext: Application
) {

    NavHost(navController = navController, startDestination = SignIn) {
        val authenticationViewModel = AuthenticationViewModel()
        val reminderViewModel = ReminderViewModel(appContext)
        composable<Authentication> {

            val viewModel = ReminderViewModel(appContext)

            auth(modifier, viewModel) {
                navController.navigate(route = SignIn)
            }
        }
        composable<SignIn> {
            LoginPage(modifier, authenticationViewModel, onLogIn =  {
                navController.navigateAndDontComeBack(Main)
            }, onUserExists = {
                navController.navigateAndDontComeBack(Main)
            }) {
                navController.navigate(route = SignUp)
            }
        }
        composable<SignUp> {
            SignupScreen(modifier, authenticationViewModel, onSignUp = {
                navController.navigate(route = Authentication)
            }) {
                navController.navigate(route = SignIn)
            }
        }
        composable<Main> {
            MainScreen(
                ReminderViewModel = reminderViewModel,
                authenticationViewModel = authenticationViewModel,
                modifier = modifier,
                onAddMed = { name, ft, st, tt, dose ->
                    navController.navigate(route = AddReminder(name, ft, st, tt, dose))
                }) { id, name, ft, st, tt, dose ->
                navController.navigate(route = EditReminder(id, name, ft, st, tt, dose))
            }
        }
        composable<EditReminder> {
            val args = it.toRoute<EditReminder>()
            EditMedicationScreen(
                modifier = modifier,
                reminderViewModel = reminderViewModel,
                authenticationViewModel = authenticationViewModel,
                id = args.id,
                medName = args.name,
                medFirstTime = args.firstTime,
                medSecondTime = args.secondTime,
                medThirdTime = args.thirdTime,
                medDose = args.dose
            ) {
                navController.popBackStack()
            }
        }
        composable<AddReminder> {
            val args = it.toRoute<EditReminder>()
            AddMedicationScreen(
                modifier = modifier,
                reminderViewModel = reminderViewModel,
                authenticationViewModel = authenticationViewModel,
                medName = args.name,
                medFirstTime = args.firstTime,
                medSecondTime = args.secondTime,
                medThirdTime = args.thirdTime,
                medDose = args.dose
            ) {
                navController.popBackStack()
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