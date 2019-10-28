package com.myteam.infra

import com.myteam.core.domain.Player
import com.myteam.core.domain.Team

interface TeamRepository {
    fun findByToken(token: String): Team?

    fun addPlayer(team: Team, player: Player): Team
}