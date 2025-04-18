package com.drcmind.bikaservices

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.drcmind.bikaservices.domain.model.User
import com.drcmind.bikaservices.ui.theme.BikaServicesTheme
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val credentialManager : CredentialManager by inject()
    private val request : GetCredentialRequest by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BikaServicesTheme {

            }
        }
    }
    private fun handleSignIn(response : GetCredentialResponse, onSignInUser : (User?)-> Unit){
        val credential = response.credential
        when(credential){
            is CustomCredential ->{
                if(credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
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
                    }catch (e : Exception){
                        onSignInUser(null)
                        Log.e("MALAKISIAPPDEBUG", "Erreur du token google de la reponse", e)
                    }

                }else{
                    onSignInUser(null)
                    Log.e("MALAKISIAPPDEBUG", "Coordonnée de connexion inconnue")
                }
            }

            else -> {
                onSignInUser(null)
                Log.e("MALAKISIAPPDEBUG", "Coordonnée de connexion inconnue")
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BikaServicesTheme {

    }
}