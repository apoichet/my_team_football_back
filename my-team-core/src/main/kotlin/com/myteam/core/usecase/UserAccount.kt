package com.myteam.core.usecase

import com.myteam.core.domain.Contact
import com.myteam.core.domain.Player
import com.myteam.core.domain.Team
import com.myteam.core.domain.User
import com.myteam.core.exception.TeamAlreadyExists
import com.myteam.core.exception.TeamNotExists
import com.myteam.core.exception.UserAccountUnknown
import com.myteam.core.exception.UserMailAlreadyExist
import com.myteam.repository.TeamRepository
import com.myteam.repository.UserRepository

class UserAccount(private val userRepository: UserRepository,
                  private val teamRepositoy: TeamRepository
) {

    fun createAccount(newUser: User): User {
        userRepository.findBy(newUser.contact.mail)?.let {
            throw UserMailAlreadyExist("User mail ${newUser.contact.mail} already exists")
        } ?: run {
            return userRepository.create(newUser)
        }
    }

    fun loginUser(mail: String, password: String): User? {
        userRepository.findBy(mail)?.let {
            if (it.password == password) return it
        }
        return null
    }

    fun closeAccount(user: User): Boolean {
        userRepository.find(user.id)?.let {
            return userRepository.delete(it)
        }
        return true
    }

    fun modifyContact(user: User, newContact: Contact): User? {
        userRepository.find(user.id)?.let { userFound ->
            //Update user contact
            userFound.contact = newContact
            return userRepository.update(userFound)
        }
        throw UserAccountUnknown("User account with mail ${user.contact.mail} not exists")
    }

    fun modifyPassword(user: User, password: String): User? {
        userRepository.find(user.id)?.let { userFound ->
            //Update user contact
            userFound.password = password
            return userRepository.update(userFound)
        }
        throw UserAccountUnknown("User account with mail ${user.contact.mail} not exists")
    }

    fun findTeam(token: String): Team? {
        return teamRepositoy.find(token)
    }

    fun createTeam(user: User, newTeam: Team): Team {
        val teamAlredyExists = user.teams.count {
            it.name === newTeam.name
        } > 0
        if (teamAlredyExists) {
            throw TeamAlreadyExists("User ${user.contact.mail} has already created a team with name ${newTeam.name}")
        }
        return teamRepositoy.create(newTeam)
    }

    fun joinTeam(player: Player, team: Team): Team {
        findTeam(team.token)?.let {
            it.players = it.players.plus(player)
            return teamRepositoy.update(it)
        }
        throw TeamNotExists("Team with name ${team.name} does not exist")
    }

}