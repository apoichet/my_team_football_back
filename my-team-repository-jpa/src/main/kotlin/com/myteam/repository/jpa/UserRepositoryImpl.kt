package com.myteam.repository.jpa

import com.myteam.core.domain.User
import com.myteam.repository.UserRepository

class UserRepositoryImpl: UserRepository  {

    override fun findBy(mail: String): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun find(id: String): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun create(domainObject: User): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(domainObject: User): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(domainObject: User): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}