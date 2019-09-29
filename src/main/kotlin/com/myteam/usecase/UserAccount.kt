package com.myteam.usecase

import com.myteam.domain.User
import com.myteam.exception.UserMailAlreadyExist
import com.myteam.exception.UserAccountUnknown
import com.myteam.port.UserRepository

class UserAccount(private val userRepository: UserRepository) {

    fun create(newUser: User): User {
        userRepository.findBy(newUser.mail)?.let {
            throw UserMailAlreadyExist("User ${newUser.mail} already exists")
        } ?: run {
            return userRepository.create(newUser)
        }
    }

    fun login(mail: String, password: String): User? {
        userRepository.findBy(mail)?.let {
            if (it.password == password) return it
        }
        return null
    }

    fun close(user: User): Boolean {
        userRepository.find(user.id)?.let {
            return userRepository.delete(it)
        }
        return true
    }

    fun modify(user: User): User? {
        userRepository.find(user.id)?.let {
            return userRepository.update(it)
        }
        throw UserAccountUnknown("User ${user.mail} account is unknown")
    }

}