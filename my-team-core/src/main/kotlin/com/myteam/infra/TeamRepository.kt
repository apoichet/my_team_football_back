package com.myteam.infra

import com.myteam.core.domain.*

interface TeamRepository {
    fun findByToken(token: String): Team?

    fun addPlayer(team: Team, player: UserTeam): Team
}