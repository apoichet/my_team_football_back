package com.myteam.repository

import com.myteam.core.domain.User

interface UserRepository : CrudRepository<User> {

    fun findBy(mail: String): User?

}