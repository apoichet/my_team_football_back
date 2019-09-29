package com.myteam.usecase

import com.myteam.domain.TeamMember
import com.myteam.domain.User
import com.myteam.exception.UserMailAlreadyExist
import com.myteam.exception.UserAccountUnknown
import com.myteam.port.TeamMemberRepository
import com.myteam.port.UserRepository

class UserAccount(private val userRepository: UserRepository,
                  private val teamMemberRepository: TeamMemberRepository) {

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

    fun modify(newUser: User): User? {
        userRepository.find(newUser.id)?.let { userFound ->
            //Update team members informations
            updateTeamMembers(userFound, newUser)
            newUser.teams = userFound.teams
            return userRepository.update(newUser)
        }
        throw UserAccountUnknown("User ${newUser.mail} account is unknown")
    }

    private fun updateTeamMembers(oldUser: User, newUser: User) {
        oldUser.teams
            .forEach { team ->
                team.players.filter { player ->
                    player.mail == oldUser.mail
                }.forEach { p ->
                    updateTeamMember(newUser, p)
                }
            }
        oldUser.teams
            .filter { team ->
                team.coach.mail == oldUser.mail
            }.forEach { team ->
                updateTeamMember(newUser, team.coach)
            }
    }

    private fun updateTeamMember(user: User, teamMember: TeamMember) {
        teamMember.mail = user.mail
        teamMember.firstName = user.firstName
        teamMember.lastName = user.lastName
        teamMember.phone = user.phone
        teamMember.adress = user.adress
        teamMember.birthdate = user.birthdate
        teamMemberRepository.update(teamMember)
    }

}