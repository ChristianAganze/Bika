package com.drcmind.bikaservices.domain.usecase

import com.drcmind.bikaservices.data.repository.BikaRepository
import com.drcmind.bikaservices.domain.model.User
import com.drcmind.bikaservices.utils.Result

class setCurrentUser(private val repository: BikaRepository) {
    suspend operator fun invoke(user: User?):Result<User?>{
        return repository.setCurrentUser(user)
    }
}