package com.myteam.application

import com.myteam.core.domain.Contact
import com.myteam.core.domain.User

interface UserRegister {

    fun createAccount(newUser: User): User?

}