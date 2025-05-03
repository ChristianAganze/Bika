package com.drcmind.bikaservices.domain.usecase.user

import com.drcmind.bikaservices.data.repository.BikaRepository
import com.drcmind.bikaservices.domain.model.User
import com.drcmind.bikaservices.utils.Result

class   GetCurrentUserUseCase(private val repository: BikaRepository){
    suspend operator fun invoke() : Result<User?>{
        return repository.getCurrentUser()
    }

}