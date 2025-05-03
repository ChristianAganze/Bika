package com.drcmind.bikaservices.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drcmind.bikaservices.R
import com.drcmind.bikaservices.R.drawable.logo
import io.ktor.websocket.Frame
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AuthenticationScreen(
    onSignInCliked : ()-> Unit
){
    val navigator = rememberSupportingPaneScaffoldNavigator()
    val  coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = navigator.canNavigateBack()) {
        coroutineScope.launch {
            navigator.navigateBack()
        }
    }

    SupportingPaneScaffold(
        value = navigator.scaffoldValue,
        directive = navigator.scaffoldDirective,
        mainPane = {
            AnimatedPane {
                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        ,
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = painterResource(R.drawable.baseline),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(0.6f),
                        contentScale = ContentScale.Fit
                    )
                    if(navigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Hidden){
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    navigator.navigateTo(SupportingPaneScaffoldRole.Supporting)
                                }
                            },
                            modifier = Modifier.padding(64.dp)
                                .align(Alignment.BottomCenter)
                        ) {
                            Text("Démarrer")
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        },
        supportingPane = {
            AnimatedPane {
                Box(
                    modifier = Modifier.padding(16.dp).fillMaxSize()
                ){
                    if(navigator.scaffoldValue[SupportingPaneScaffoldRole.Main] == PaneAdaptedValue.Hidden){
                        IconButton(
                            onClick = {
                               // navigator.navigateBack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.padding(32.dp).align(Alignment.Center),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Bienvenu sur l'Application BikaServices",
                            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 46.sp,  lineHeight = 64.sp),
                            fontWeight = FontWeight.W400
                        )

                        HorizontalDivider(modifier = Modifier.width(40.dp))

                        Text(
                            text = "Une Appliuccation concu pour facilites nos utilisateur à partage leurs capacites des services auquel ils sont capables ainsi ",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.W400
                        )

                        Button(
                            onClick = {
                                onSignInCliked()
                            }
                        ) {
                            Text("Connectez-vous")
                        }
                    }
                }
            }
        }
    )
}