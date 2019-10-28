package com.myteam.repository.jpa.impl

import com.myteam.core.domain.Player
import com.myteam.core.domain.Team
import com.myteam.infra.TeamRepository
import javax.persistence.EntityManager

class TeamRepositoryImpl(val em: EntityManager) : TeamRepository {

    override fun findByToken(token: String): Team? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addPlayer(team: Team, player: Player): Team {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}