package com.drcmind.bikaservices.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drcmind.bikaservices.domain.model.User
import com.drcmind.bikaservices.domain.usecase.getCurrentUser
import com.drcmind.bikaservices.domain.usecase.setCurrentUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthentificationViewModel(
    private val setCurrentUser: setCurrentUser,
    private  val getCurrentUser: getCurrentUser
):ViewModel() {
    private var _currentUser : MutableStateFlow<User?> = MutableStateFlow(null)
    val currentUser = _currentUser.asStateFlow()

    private var _isLoading : MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading
        .onStart { getCurrentUser() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            true
        )

    fun login(user: User?){
        viewModelScope.launch{
            val result = setCurrentUser(user)
            when (result){
                is Result.Error -> {
                    Log.d("MALAKISIAPPDEBUG", result.message.toString())
                }
                is Result.Loading -> TODO()
                is Result.Success -> {
                    _currentUser.value = result.data
                }
            }
        }
    }

    fun logout(){
        viewModelScope.launch{
            val result = setCurrentUser(null)
            when (result){
                is Result.Error -> {
                    Log.d("MALAKISIAPPDEBUG", result.message.toString())
                }
                is Result.Loading -> TODO()
                is Result.Success -> {
                    _currentUser.value = result.data
                }
            }
        }
    }

    fun getCurrentUser(){
        viewModelScope.launch{
            val result = getCurrentUser()
            when(result){
                is Result.Error -> {
                    _isLoading.value = false
                    _currentUser.value = null
                    Log.d("MALAKISIAPPDEBUG", result.message.toString())
                }
                is Result.Loading -> TODO()
                is Result.Success -> {
                    _isLoading.value = false
                    _currentUser.value = result.data
                }
            }
        }
    }
}