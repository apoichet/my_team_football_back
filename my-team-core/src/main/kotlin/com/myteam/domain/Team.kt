package com.myteam.domain

import com.myteam.enums.PaymentType
import java.time.LocalDateTime

class Team(
    var token: String,
    var creationDate: LocalDateTime,
    var name: String,
    var logo: String? = null,
    var colors: List<String> = emptyList(),
    var pictures: List<String> = emptyList(),
    var licenceAmount: Float,
    var homeStadium: Stadium,
    var president: TeamMember,
    var coach: TeamMember? = null,
    var players: List<Player> = emptyList(),
    var rib: Rib? = null)

class Stadium(var name: String, var adress: Adress)

class Rib(var iban: String)