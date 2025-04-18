package com.drcmind.bikaservices.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.drcmind.bikaservices.data.datasource.local.datastore.PreferenceKeys
import com.drcmind.bikaservices.domain.model.User
import com.drcmind.bikaservices.utils.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class BikaRepositoryImpl(
    private val dataStore : DataStore<Preferences>
    ):BikaRepository {
    override suspend fun setCurrentUser(user: User?): Result<User?> {
        return try {
            dataStore.edit { preferences->
                preferences[PreferenceKeys.userName] = user?.name ?: ""
                preferences[PreferenceKeys.userEmail] = user?.email ?: ""
                preferences[PreferenceKeys.userProfilePicture] = user?.profilePictureUri ?: ""
                preferences[PreferenceKeys.isLoggedIn] = user?.isLoggedIn == true
            }
            Result.Success(user)
        }catch (e : Exception){
            Result.Error("Erreur lors de la modification de l'utilisateur : ${e.message}")
        }
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return try {
            val currentUser = dataStore.data.map { preferences->
                User(
                    name = preferences[PreferenceKeys.userName] ?: "",
                    email = preferences[PreferenceKeys.userEmail] ?: "",
                    profilePictureUri = preferences[PreferenceKeys.userProfilePicture] ?: "",
                    isLoggedIn = preferences[PreferenceKeys.isLoggedIn] == true
                )
            }.first()
            Result.Success(if(currentUser.isLoggedIn) currentUser else null)
        }catch (e : Exception){
            Result.Error("Erreur lors de la récupération de l'utilisateur : ${e.message}")
        }
    }

}