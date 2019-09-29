package com.myteam.port

import com.myteam.domain.User

interface UserRepository : CrudRepository<User> {

    fun findBy(mail: String): User?

}