package com.myteam.application

import com.myteam.core.domain.*

interface UserLogin {

    fun loginUser(mail: String, password: String): User?

}