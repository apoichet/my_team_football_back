package com.myteam.core.usecase

import com.myteam.core.domain.Contact
import com.myteam.core.domain.Player
import com.myteam.core.domain.Team
import com.myteam.core.domain.User
import com.myteam.core.exception.TeamAlreadyExists
import com.myteam.core.exception.UserMailAlreadyExist
import com.myteam.infra.TeamRepository
import com.myteam.infra.UserRepository

class UserAccount(private val userRepository: UserRepository,
                  private val teamRepositoy: TeamRepository
) {

    fun createAccount(newUser: User): User? {
        userRepository.findByMail(newUser.contact.mail)?.let {
            throw UserMailAlreadyExist("User mail ${newUser.contact.mail} already exists")
        } ?: run {
            return userRepository.create(newUser)
        }
    }

    fun loginUser(mail: String, password: String): User? {
        userRepository.findByMail(mail)?.let {
            if (it.password == password) return it
        }
        return null
    }

    fun closeAccount(user: User): Boolean {
        return userRepository.delete(user)
    }

    fun modifyContact(user: User, newContact: Contact): User? {
        return userRepository.updateContact(user, newContact)
        //throw UserAccountUnknown("User account with mail ${user.contact.mail} not exists")
    }

    fun modifyPassword(user: User, newPassword: String): User? {
        return userRepository.updatePassword(user, newPassword)
        //throw UserAccountUnknown("User account with mail ${user.contact.mail} not exists")
    }

    fun findTeam(token: String): Team? {
        return teamRepositoy.findByToken(token)
    }

    fun createTeam(user: User, newTeam: Team): Team? {
        val teamAlredyExists = user.teams.count {
            it.name === newTeam.name
        } > 0
        if (teamAlredyExists) {
            throw TeamAlreadyExists("User ${user.contact.mail} has already created a team with name ${newTeam.name}")
        }
        return teamRepositoy.create(newTeam)
    }


    fun joinTeam(team: Team, player: Player): Team {
        return teamRepositoy.addPlayer(team, player)
        //throw TeamNotExists("Team with name ${team.name} does not exist")
    }

}