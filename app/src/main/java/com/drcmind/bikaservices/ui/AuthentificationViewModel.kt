package com.drcmind.bikaservices.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drcmind.bikaservices.domain.model.User
import com.drcmind.bikaservices.domain.usecase.user.GetCurrentUserUseCase
import com.drcmind.bikaservices.domain.usecase.user.SetCurrentUserUseCase
import com.drcmind.bikaservices.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthentificationViewModel(
    private val setCurrentUserUseCase: SetCurrentUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
  //private val useCases: UseCases
):ViewModel() {
    private var _currentUser: MutableStateFlow<User?> = MutableStateFlow(null)
    val currentUser = _currentUser.asStateFlow()

    private var _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading
        .onStart { getCurrentUser() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            true
        )
    fun login(user: User?) {
        viewModelScope.launch {
            when(val result = setCurrentUserUseCase(user)){
               is Result.Error<*> -> {
                   Log.d("BikaServicesDEBUG",result.message.toString())
               }
               is Result.Loading<*> -> TODO()
               is Result.Success<*> -> {
                   _currentUser.value = result.data
               }
           }
        }
    }
    fun logout() {
        viewModelScope.launch {
            when (val result = setCurrentUserUseCase(null)) {
                is Result.Error -> {
                    Log.d("BikaServicesDEBUG", result.message.toString())
                }
                is Result.Loading -> {

                }
                is Result.Success -> {
                    _currentUser.value = result.data
                }
            }
        }
    }
    fun getCurrentUser() {
        viewModelScope.launch {
            when (val result = getCurrentUserUseCase()) {
                is Result.Error -> {
                    _isLoading.value = false
                    _currentUser.value = null
                    Log.d("BikaServicesDEBUG", result.message.toString())
                }
                is Result.Loading -> {}
                is Result.Success -> {
                    _isLoading.value = false
                    _currentUser.value = result.data
                }
            }
        }
    }
}