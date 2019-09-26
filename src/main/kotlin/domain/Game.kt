package domain

import java.time.LocalDateTime

data class Game(
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

data class PlayerGame(
    val player: Player,
    var state: PlayerState,
    var arriving: LocalDateTime,
    var goals: List<Goal>,
    var decisivePass: Int,
    var yellowCard: List<Int>,
    var redCard: Int,
    var minuteReferee: Int,
    var positions: List<PlayerPosition>,
    var coachMark: Int,
    var teamMark: Int
)

enum class GameConclusion {
    VICTORY, DEFEAT, EQUALITY
}

enum class GamePlace {
    HOME, AWAY, NEUTRAL
}

enum class GameType {
    CHAMPIONSHIP, CUP, FRIENDLY
}

data class Goal(var member: GoalMember, var type: GoalType)

enum class GoalMember {
    HEAD, LEFT_FOOT, RIGHT_FOOT
}

enum class GoalType {
    IN_GAME, KICK, PENALTY
}

enum class PlayerState {
    PRESENT, ABSENT, INJURED, NOT_SELECTED
}


