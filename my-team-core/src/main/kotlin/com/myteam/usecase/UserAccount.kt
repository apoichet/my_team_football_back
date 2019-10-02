package com.myteam.usecase

import com.myteam.domain.Player
import com.myteam.domain.Team
import com.myteam.domain.TeamMember
import com.myteam.domain.User
import com.myteam.exception.TeamAlreadyExists
import com.myteam.exception.TeamNotExists
import com.myteam.exception.UserMailAlreadyExist
import com.myteam.exception.UserAccountUnknown
import com.myteam.port.TeamMemberRepository
import com.myteam.port.TeamRepository
import com.myteam.port.UserRepository

class UserAccount(private val userRepository: UserRepository,
                  private val teamRepositoy: TeamRepository,
                  private val teamMemberRepository: TeamMemberRepository) {

    fun createAccount(newUser: User): User {
        userRepository.findBy(newUser.mail)?.let {
            throw UserMailAlreadyExist("User mail ${newUser.mail} already exists")
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

    fun modifyProfile(newUser: User): User? {
        userRepository.find(newUser.id)?.let { userFound ->
            //Update team members informations
            updateTeamMembers(userFound, newUser)
            newUser.teams = userFound.teams
            return userRepository.update(newUser)
        }
        throw UserAccountUnknown("User account with mail ${newUser.mail} not exists")
    }

    fun findTeam(token: String): Team? {
        return teamRepositoy.find(token)
    }

    fun createTeam(user: User, newTeam: Team): Team {
        val teamAlredyExists = user.teams.count {
            it.name === newTeam.name
        } > 0
        if (teamAlredyExists) {
            throw TeamAlreadyExists("User ${user.lastName} ${user.firstName} has already created a team with name ${newTeam.name}")
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