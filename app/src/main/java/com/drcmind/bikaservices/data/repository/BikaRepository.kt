package com.drcmind.bikaservices.data.repository

import com.drcmind.bikaservices.domain.model.User
import com.drcmind.bikaservices.utils.Result

interface BikaRepository {
    suspend fun setCurrentUserUseCase(user: User?) : Result<User?>

    suspend fun getCurrentUserUseCase() : Result<User?>

}