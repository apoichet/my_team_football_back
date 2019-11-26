package com.myteam.core.domain

import com.myteam.core.enums.*
import java.time.LocalDateTime

class Team(
    var token: String = "",
    val creationDate: LocalDateTime = LocalDateTime.now(),
    val season: String,
    var name: String,
    var logo: String? = null,
    var colors: List<String> = emptyList(),
    var pictures: List<String> = emptyList(),
    var licenceAmount: Float = 0F,
    var homeStadium: Stadium,
    var president: UserTeam,
    var coach: UserTeam? = null,
    var players: List<UserTeam> = emptyList(),
    var games: List<Game> = emptyList(),
    var contributions: List<UserTeamContribution> = emptyList(),
    var rib: Rib? = null)

class Stadium(var name: String, var address: Address)

class Rib(var iban: String)

class UserTeamContribution(val userTeam: UserTeam,
                           val amount: Float,
                           var paymentType: PaymentType
)