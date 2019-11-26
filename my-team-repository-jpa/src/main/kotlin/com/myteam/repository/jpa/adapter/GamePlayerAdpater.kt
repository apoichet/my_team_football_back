package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.GamePlayer
import com.myteam.core.enums.*
import com.myteam.repository.jpa.entities.PlayerPosition

class GamePlayerAdpater:
    RepositoryAdapter<GamePlayer, com.myteam.repository.jpa.entities.GamePlayer> {

    private val userTeamAdapter = UserTeamAdapter()
    private val goalAdapter = GoalAdapter()

    override fun convertDomainObjectToData(domainObject: GamePlayer): com.myteam.repository.jpa.entities.GamePlayer {
        return com.myteam.repository.jpa.entities.GamePlayer(
            id = null,
            state = domainObject.state.name,
            arriving = domainObject.arriving,
            decisivePass = domainObject.decisivePass,
            yellowCard = domainObject.yellowCard,
            redCard = domainObject.redCard,
            minuteReferee = domainObject.minuteReferee,
            coachRate = domainObject.coachRate,
            goals = domainObject.goals.map { g -> goalAdapter.convertDomainObjectToData(g) },
            positions = domainObject.positions.map { p -> PlayerPosition(id = null, position = p.name) },
            player = userTeamAdapter.convertDomainObjectToData(domainObject.player)
        )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.GamePlayer): GamePlayer {
        return GamePlayer(
            state = PlayerGameState.valueOf(data.state),
            arriving = data.arriving,
            decisivePass = data.decisivePass,
            yellowCard = data.yellowCard,
            redCard = data.redCard,
            minuteReferee = data.minuteReferee,
            coachRate = data.coachRate,
            goals = data.goals.map { g -> goalAdapter.convertDataToDomainObject(g) },
            positions = data.positions.map { p -> com.myteam.core.enums.PlayerPosition.valueOf(p.position) },
            player = userTeamAdapter.convertDataToDomainObject(data.player)
        )
    }
}