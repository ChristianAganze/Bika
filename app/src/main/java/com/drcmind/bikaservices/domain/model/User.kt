package com.drcmind.bikaservices.domain.model


 
data class User(
    val name : String,
    val email : String,
    val isLoggedIn : Boolean,
    val profilePictureUri : String = "",
    //val bio: String? = null,
    //val role: Role
)

