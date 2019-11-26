package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.*
import com.myteam.core.enums.*

class GameAdapter: RepositoryAdapter<Game, com.myteam.repository.jpa.entities.Game> {

    private val stadiumAdapter = StadiumAdapter()
    private val gamePlayerAdapter = GamePlayerAdpater()

    override fun convertDomainObjectToData(domainObject: Game): com.myteam.repository.jpa.entities.Game {
        return com.myteam.repository.jpa.entities.Game(
            id = null,
            nameOpponent = domainObject.nameOpponent,
            type = domainObject.type.name,
            place = domainObject.place.name,
            start = domainObject.startGame,
            rendezVous = domainObject.rendezVous,
            nbGoalTeam = domainObject.nbGoalTeam,
            nbrGoalOpponent = domainObject.nbGoalOpponent,
            stadium = stadiumAdapter.convertDomainObjectToData(domainObject.stadium),
            gamePlayers = domainObject.gamePlayers.map { g -> gamePlayerAdapter.convertDomainObjectToData(g) }
        )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.Game): Game {
        return Game(
            nameOpponent = data.nameOpponent,
            type = GameType.valueOf(data.type),
            place = GamePlace.valueOf(data.place),
            startGame = data.start,
            rendezVous = data.rendezVous,
            nbGoalTeam = data.nbGoalTeam,
            nbGoalOpponent = data.nbrGoalOpponent,
            stadium = stadiumAdapter.convertDataToDomainObject(data.stadium),
            gamePlayers = data.gamePlayers.map { g -> gamePlayerAdapter.convertDataToDomainObject(g) }
        )
    }
}