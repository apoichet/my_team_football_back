package com.myteam.domain

import java.time.LocalDateTime

open class TeamMember(
    open val contact: Contact,
    open var creationDate: LocalDateTime = LocalDateTime.now(),
    open var bestQuality: String = "",
    open var worstDefault: String = "",
    open var verbatim: String = ""
)

class Player (
    override val contact: Contact,
    override var creationDate: LocalDateTime = LocalDateTime.now(),
    override var bestQuality: String = "",
    override var worstDefault: String = "",
    override var verbatim: String = "",
    var strongFoot: PlayerFoot,
    var weight: Int = 0,
    var height: Int = 0,
    var originalPosition: PlayerPosition,
    var positions: List<PlayerPosition>
): TeamMember(
    contact,
    creationDate,
    bestQuality,
    worstDefault,
    verbatim
)

enum class PlayerPosition(val position: String) {
    GK("Goal Keeper")
}

enum class PlayerFoot {
    LEFT, RIGHT, BOTH
}


class Adress(val adress: String, val city: String, val zipCode: String, val country: String = "France")