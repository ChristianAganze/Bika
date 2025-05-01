package com.drcmind.bikaservices

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drcmind.bikaservices.domain.model.User
import com.drcmind.bikaservices.ui.AuthentificationViewModel
import com.drcmind.bikaservices.ui.screens.AppScreen
import com.drcmind.bikaservices.ui.screens.AuthenticationScreen
import com.drcmind.bikaservices.ui.theme.BikaServicesTheme
import com.drcmind.bikaservices.ui.util.AuthentificationRoute
import com.drcmind.bikaservices.ui.util.BikaRoutes
import com.drcmind.bikaservices.ui.util.LoadingRoute
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val credentialManager: CredentialManager by inject()
    private val request: GetCredentialRequest by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val viewModel: AuthentificationViewModel by viewModel()
            val navController = rememberNavController()
            val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()
            val currentUser = viewModel.currentUser.collectAsStateWithLifecycle()

            splashScreen.setKeepOnScreenCondition {
                isLoading.value
            }
            BikaServicesTheme {

                NavHost(navController = navController, startDestination = LoadingRoute){
                    composable<LoadingRoute>{
                        LaunchedEffect(isLoading.value) {
                            if(currentUser.value!=null){
                                navController.navigate(BikaRoutes){
                                    popUpTo(LoadingRoute){inclusive=true}
                                }
                            }else if(currentUser.value==null && !isLoading.value){
                                navController.navigate(AuthentificationRoute){
                                    popUpTo(LoadingRoute){inclusive=true}
                                }
                            }
                        }
                    }

                    composable<AuthentificationRoute>{

                        LaunchedEffect(currentUser.value) {
                            if(currentUser.value != null){
                                navController.navigate(BikaRoutes){
                                    popUpTo(AuthentificationRoute){
                                        inclusive = true
                                    }
                                }
                            }
                        }

                        AuthenticationScreen(
                            onSignInCliked = {
                                lifecycleScope.launch{
                                    try {
                                        val credentialResponse = credentialManager.getCredential(
                                            request = request,
                                            context = this@MainActivity
                                        )
                                        handleSignIn(
                                            response = credentialResponse,
                                            onSignInUser = {user->
                                                viewModel.login(user)
                                                navController.navigate(BikaRoutes){
                                                    popUpTo(BikaRoutes)
                                                }
                                            }
                                        )
                                    }catch (e : Exception){
                                        print(e)
                                    }
                                }

                            }
                        )
                    }

                   composable<BikaRoutes>{

                        LaunchedEffect(currentUser.value) {
                            if(currentUser.value == null){
                                navController.navigate(AuthentificationRoute){
                                    popUpTo(BikaRoutes){
                                        inclusive = true
                                    }
                                }
                            }
                        }
                        AppScreen(
                            currentUser = currentUser.value,
                            onSignOutClicked = {
                                viewModel.logout()
                            }
                        )
                    }
                }

            }
        }
    }


    private fun handleSignIn(response: GetCredentialResponse, onSignInUser: (User?) -> Unit) {
        val credential = response.credential
        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        val user = User(
                            name = googleIdTokenCredential.displayName ?: "Pas de nom",
                            email = googleIdTokenCredential.id,
                            isLoggedIn = true,
                            profilePictureUri = googleIdTokenCredential.profilePictureUri.toString()
                        )
                        onSignInUser(user)
                    } catch (e: Exception) {
                        onSignInUser(null)
                        Log.e("BikaServicesDEBUG", "Erreur du token google de la reponse", e)
                    }

                } else {
                    onSignInUser(null)
                    Log.e("BikaServicesDEBUG", "Coordonnée de connexion inconnue")
                }
            }

            else -> {
                onSignInUser(null)
                Log.e("BikaServicesDEBUG", "Coordonnée de connexion inconnue")
            }
        }
    }
}
