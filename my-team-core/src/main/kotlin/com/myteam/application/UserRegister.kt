package com.myteam.application

import com.myteam.core.domain.*

interface UserRegister {

    fun createAccount(newUser: User): User?

}