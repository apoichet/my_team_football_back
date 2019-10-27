package com.myteam.application

import com.myteam.core.domain.Player
import com.myteam.core.domain.Team
import com.myteam.core.domain.User

interface UserTeamRegister {

    fun createTeam(user: User, newTeam: Team): Team?

    fun findTeamByToken(token: String): Team?

    fun joinTeam(team: Team, player: Player): Team?

}