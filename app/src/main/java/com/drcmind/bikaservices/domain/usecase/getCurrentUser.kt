package com.drcmind.bikaservices.domain.usecase

import com.drcmind.bikaservices.data.repository.BikaRepository
import com.drcmind.bikaservices.domain.model.User
import com.drcmind.bikaservices.utils.Result

class getCurrentUser(private val repository: BikaRepository){
    suspend operator fun invoke() : Result<User?>{
        return repository.getCurrentUser()
    }

}