package com.example.projectkiwi.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.projectkiwi.utils.Constants.JWT_TOKEN_KEY
import com.example.projectkiwi.utils.Constants.NAME_KEY
import kotlinx.coroutines.flow.first

class SessionManager(val context:Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("session_manager")

    suspend fun updateSession(token:String,username:String){

         val jwtTokenKey= stringPreferencesKey(JWT_TOKEN_KEY)
         val nameKey= stringPreferencesKey(NAME_KEY)
        context.dataStore.edit {preferences->
            preferences[jwtTokenKey]=token
            preferences[nameKey]=username
        }
    }

    suspend fun getJwtToken():String?{
        val jwtTokenKey= stringPreferencesKey(JWT_TOKEN_KEY)
        val preferences= context.dataStore.data.first()
        return preferences[jwtTokenKey]

    }

    suspend fun getNameToken():String?{
        val nameKey= stringPreferencesKey(NAME_KEY)
        val preferences= context.dataStore.data.first()
        return preferences[nameKey]

    }
    suspend fun logout(){
        context.dataStore.edit {
            it.clear()
        }
    }
}