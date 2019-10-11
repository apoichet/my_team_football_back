package com.myteam.domain

import com.myteam.enums.*
import java.time.LocalDateTime

class Game(
    val nameOpponent: String,
    val type: GameType,
    var place: GamePlace,
    var startGame: LocalDateTime,
    var rendezVous: LocalDateTime,
    var score: String?,
    var conclusion: GameConclusion?,
    var adressStadium: Adress,
    var gamePlayers: List<PlayerGame>
)

class PlayerGame(
    val player: Player,
    var state: PlayerGameState,
    var arriving: LocalDateTime,
    var goals: List<Goal>,
    var decisivePass: Int,
    var yellowCard: List<Int>,
    var redCard: Int,
    var minuteReferee: Int,
    var positions: List<PlayerPosition>,
    var coachRate: Int
)

class Goal(var member: GoalMember,
           var type: GoalType)




