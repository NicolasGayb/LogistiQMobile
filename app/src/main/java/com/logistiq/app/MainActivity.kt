package com.logistiq.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.logistiq.app.ui.theme.LogistiQTheme
import com.logistiq.app.ui.home.HomeScreen
import com.logistiq.app.ui.login.LoginScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogistiQTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ){
                    composable("home"){
                        HomeScreen(
                            onGoToLogin = {
                                navController.navigate("login")
                            }
                        )
                    }
                    composable("login"){
                        LoginScreen (
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }

            }
        }
    }
}