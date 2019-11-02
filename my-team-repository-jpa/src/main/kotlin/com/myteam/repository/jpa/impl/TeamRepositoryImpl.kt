package com.myteam.repository.jpa.impl

import com.myteam.core.domain.Player
import com.myteam.core.domain.Team
import com.myteam.infra.TeamRepository
import com.myteam.repository.jpa.adapter.TeamAdapter
import java.lang.IllegalStateException
import javax.persistence.EntityManager

class TeamRepositoryImpl(val em: EntityManager) : TeamRepository {

    private val teamAdapter = TeamAdapter()

    override fun findByToken(token: String): Team? {
       val team = em.find(com.myteam.repository.jpa.entities.Team::class.java, token)
        team?.let {
            return teamAdapter.convertDataToDomainObject(team)
        }
       return null
    }

    override fun addPlayer(team: Team, player: Player): Team {
        findByToken(team.token)?.let {
            team.players = team.players.plus(player)
            val data = teamAdapter.convertDomainObjectToData(team)
            em.persist(data)
            return teamAdapter.convertDataToDomainObject(data)
        }
        throw IllegalStateException("team with token '${team.token}' does not exist")
    }

}