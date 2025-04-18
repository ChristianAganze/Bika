package com.drcmind.bikaservices.data.datasource.local.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.datastore by preferencesDataStore("Settings")