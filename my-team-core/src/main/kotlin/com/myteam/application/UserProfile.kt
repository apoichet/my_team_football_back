package com.myteam.application

import com.myteam.core.domain.*

interface UserProfile {

    fun closeAccount(user: User): Boolean

    fun modifyProfile(user: User, newProfile: UserTeam): User?

    fun modifyPassword(user: User, newPassword: String): User?

}