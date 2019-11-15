package com.myteam.application

import com.myteam.core.domain.*

interface UserProfile {

    fun closeAccount(user: User): Boolean

    fun modifyContact(user: User, newContact: Contact): User?

    fun modifyPassword(user: User, newPassword: String): User?

}