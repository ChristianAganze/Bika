package com.drcmind.bikaservices.di

import androidx.credentials.CredentialManager
import com.drcmind.bikaservices.data.datasource.local.datastore.datastore
import com.drcmind.bikaservices.data.repository.BikaRepository
import com.drcmind.bikaservices.data.repository.BikaRepositoryImpl
import com.drcmind.bikaservices.domain.usecase.user.GetCurrentUserUseCase
import com.drcmind.bikaservices.domain.usecase.user.SetCurrentUserUseCase
import com.drcmind.bikaservices.ui.AuthentificationViewModel
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

//231241310295-418q4q792gnanppk76fllej4ntkrt8jd.apps.googleusercontent.com

val appModule = module {
    factory {
        CredentialManager.create(androidContext())
    }
    factory {
        val googleOptions = GetSignInWithGoogleOption.Builder(
            "231241310295-418q4q792gnanppk76fllej4ntkrt8jd.apps.googleusercontent.com"
        ).build()

        androidx.credentials.GetCredentialRequest.Builder()
            .addCredentialOption(googleOptions)
            .build()
    }
    single {
        androidContext().datastore
    }

    factory<BikaRepository> {
        BikaRepositoryImpl(get())
    }

    //
    factoryOf(::GetCurrentUserUseCase)
    factoryOf(::SetCurrentUserUseCase)
    // ViewModel
    viewModelOf(::AuthentificationViewModel)


}