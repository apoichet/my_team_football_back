package com.myteam.infra

import com.myteam.core.domain.*

interface UserRepository {

    fun findByMail(mail: String): User?

    fun updateProfile(userToModified: User, newProfile: UserTeam): User

    fun updatePassword(userToModified: User, newPassword: String): User

    fun addTeam(userWithNewTeam: User, newTeam: Team): Team

    fun delete(userToDelete: User): Boolean

    fun create(newUser: User): User?

}