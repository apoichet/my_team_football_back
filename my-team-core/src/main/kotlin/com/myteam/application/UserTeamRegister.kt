package com.myteam.application

import com.myteam.core.domain.*

interface UserTeamRegister {

    fun createTeam(user: User, newTeam: Team): Team?

    fun findTeamByToken(token: String): Team?

    fun joinTeam(team: Team, player: UserTeam): Team?

}