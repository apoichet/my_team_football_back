package com.myteam.core.usecase

import com.myteam.application.*
import com.myteam.core.domain.*
import com.myteam.core.exception.*
import com.myteam.infra.*

class UserAccount(private val userRepository: UserRepository,
                  private val teamRepository: TeamRepository) :
UserRegister, UserLogin, UserProfile, UserTeamRegister{

    override fun createAccount(newUser: User): User? {
        userRepository.findByMail(newUser.userTeam.contact.mail)?.let {
            throw UserMailAlreadyExist("User mail ${newUser.userTeam.contact.mail} already exists")
        } ?: run {
            return userRepository.create(newUser)
        }
    }

    override fun loginUser(mail: String, password: String): User? {
        userRepository.findByMail(mail)?.let {
            if (it.password == password) return it
        }
        return null
    }

    override fun closeAccount(user: User): Boolean {
        return userRepository.delete(user)
    }

    override fun modifyContact(user: User, newContact: Contact): User? {
        if(newContact.mail == user.userTeam.contact.mail) {
            userRepository.findByMail(newContact.mail)?.let {
                throw UserMailAlreadyExist("User mail ${newContact.mail} already exists")
            }
        }
        return userRepository.updateContact(user, newContact)
    }

    override fun modifyPassword(user: User, newPassword: String): User? {
        return userRepository.updatePassword(user, newPassword)
    }

    override fun findTeamByToken(token: String): Team? {
        return teamRepository.findByToken(token)
    }

    override fun createTeam(user: User, newTeam: Team): Team? {
        val teamAlreadyExists = user.teams.count {
            it.name === newTeam.name && it.season === newTeam.season
        } > 0
        if (teamAlreadyExists) {
            throw TeamAlreadyExists("User ${user.userTeam.contact.mail} " +
                    "has already created a team with name ${newTeam.name} and season " +
                    newTeam.season
            )
        }
        return userRepository.addTeam(user, newTeam)
    }


    override fun joinTeam(team: Team, player: UserTeam): Team? {
        return teamRepository.addPlayer(team, player)
    }

}