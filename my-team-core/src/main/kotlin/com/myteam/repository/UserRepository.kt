package com.myteam.repository

import com.myteam.domain.User

interface UserRepository : CrudRepository<User> {

    fun findBy(mail: String): User?

}