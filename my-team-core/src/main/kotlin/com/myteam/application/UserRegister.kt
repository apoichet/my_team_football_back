package com.myteam.application

import com.myteam.core.domain.Contact
import com.myteam.core.domain.User

interface UserRegister {

    fun createAccount(newUser: User): User?

    fun loginUser(mail: String, password: String): User?

    fun closeAccount(user: User): Boolean

    fun modifyContact(user: User, newContact: Contact): User?

    fun modifyPassword(user: User, newPassword: String): User?


}