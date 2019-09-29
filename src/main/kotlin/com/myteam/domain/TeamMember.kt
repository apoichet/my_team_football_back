package com.myteam.domain

import java.time.LocalDate
import java.time.LocalDateTime


abstract class TeamMember(
    open var mail: String,
    open var firstName: String,
    open var lastName: String,
    open var birthdate: LocalDate,
    open var phone: String,
    open var adress: List<Adress>,
    open var creationDate: LocalDateTime,
    open var bestQuality: String = "",
    open var worstDefault: String = "",
    open var verbatim: String = ""
)

data class Player (
    override var mail: String,
    override var firstName: String,
    override var lastName: String,
    override var birthdate: LocalDate,
    override var phone: String,
    override var adress: List<Adress>,
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
    mail,
    firstName,
    lastName,
    birthdate,
    phone,
    adress,
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

data class Coach (
    override var mail: String,
    override var firstName: String,
    override var lastName: String,
    override var birthdate: LocalDate,
    override var phone: String,
    override var adress: List<Adress>,
    override var creationDate: LocalDateTime = LocalDateTime.now(),
    override var bestQuality: String = "",
    override var worstDefault: String = "",
    override var verbatim: String = ""
): TeamMember(
    mail,
    firstName,
    lastName,
    birthdate,
    phone,
    adress,
    creationDate,
    bestQuality,
    worstDefault,
    verbatim
)


class Adress(val adress: String, val city: String, val zipCode: String, val country: String = "France")