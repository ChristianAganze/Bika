package com.drcmind.bikaservices.data.datasource.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey

object PreferenceKeys {
    val userEmail = stringPreferencesKey("user_email")
    val userName = stringPreferencesKey("user_name")
    val isLoggedIn = booleanPreferencesKey("is_logged_in")
    val userProfilePicture = stringPreferencesKey("user_profile_image")
}