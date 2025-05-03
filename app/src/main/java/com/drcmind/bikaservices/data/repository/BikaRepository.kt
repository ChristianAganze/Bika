package com.drcmind.bikaservices.data.repository

import com.drcmind.bikaservices.domain.model.User
import com.drcmind.bikaservices.domain.usecase.user.GetCurrentUserUseCase
import com.drcmind.bikaservices.utils.Result

interface BikaRepository {
    suspend fun setCurrentUser(user: User?) : Result<User?>

    suspend fun getCurrentUser() : Result<User?>

}