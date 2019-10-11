package com.myteam.core.domain

import com.myteam.core.enums.*
import java.time.LocalDateTime

class Game(
    val nameOpponent: String,
    val type: GameType,
    var place: GamePlace,
    var startGame: LocalDateTime,
    var rendezVous: LocalDateTime? = null,
    var nbrGoalTeam: Int = 0,
    var nbrGoalOpponent: Int = 0,
    var adressStadium: Adress,
    var gamePlayers: List<PlayerGame> = emptyList()
)

class PlayerGame(
    val player: Player,
    var state: PlayerGameState = PlayerGameState.ABSENT,
    var arriving: LocalDateTime,
    var goals: List<Goal> = emptyList(),
    var decisivePass: Int = 0,
    var yellowCard: Int = 0,
    var redCard: Int = 0,
    var minuteReferee: Int = 0,
    var coachRate: Int = 5,
    var positions: List<PlayerPosition> = emptyList()
    )

class Goal(var member: GoalMember,
           var type: GoalType
)




