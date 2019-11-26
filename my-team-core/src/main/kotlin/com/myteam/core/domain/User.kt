package com.myteam.core.domain

import com.myteam.core.enums.*
import java.time.LocalDate
import java.time.LocalDateTime

class User(
    var creationDate: LocalDateTime = LocalDateTime.now(),
    var password: String,
    var userTeam: UserTeam,
    var teams: List<Team> = emptyList()
)

class UserTeam(
    var contact: Contact,
    var bestQuality: String = "",
    var worstDefault: String = "",
    var verbatim: String = "",
    var birthDate: LocalDate = LocalDate.of(1970, 1, 1),
    var playerInfo: PlayerInfo? = null
)

class PlayerInfo(
    var strongFoot: PlayerFoot,
    var weight: Int? = 0,
    var height: Int? = 0,
    var originalPosition: PlayerPosition,
    var otherPositions: List<PlayerPosition>? = emptyList()
)

class Contact(
    var mail: String,
    var firstName: String,
    var lastName: String,
    var phone: String = "",
    var addresses: List<Address> = emptyList()
)

class Address(
    var address: String,
    var city: String,
    var zipCode: String,
    var country: String = "France")


