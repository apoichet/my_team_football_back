package com.myteam.domain

import java.time.LocalDateTime


abstract class TeamMember(
    creationDate: LocalDateTime,
    bestQuality: String = "",
    worstDefault: String = "",
    verbatim: String = ""
)

data class Player (
    val creationDate: LocalDateTime = LocalDateTime.now(),
    var bestQuality: String = "",
    var worstDefault: String = "",
    var verbatim: String = "",
    var strongFoot: PlayerFoot,
    var weight: Int = 0,
    var height: Int = 0,
    var originalPosition: PlayerPosition,
    var positions: List<PlayerPosition>
): TeamMember(creationDate, bestQuality, worstDefault, verbatim)

enum class PlayerPosition(val position: String) {
    GK("Goal Keeper")
}

enum class PlayerFoot {
    LEFT, RIGHT, BOTH
}

data class Coach (
    val creationDate: LocalDateTime = LocalDateTime.now(),
    var bestQuality: String = "",
    var worstDefault: String = "",
    var verbatim: String = ""
): TeamMember(creationDate, bestQuality, worstDefault, verbatim)


class Adress(val adress: String, val city: String, val zipCode: String, val country: String = "France")