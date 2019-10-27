package com.myteam.infra

import com.myteam.core.domain.Contact
import com.myteam.core.domain.User

interface UserRepository {

    fun findByMail(mail: String): User?

    fun updateContact(userToModified: User, newContact: Contact): User

    fun updatePassword(userToModified: User, newPassword: String): User

    fun delete(userToDelete: User): Boolean

    fun create(newUser: User): User?

}