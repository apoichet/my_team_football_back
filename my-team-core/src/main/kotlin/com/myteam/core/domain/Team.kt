package com.myteam.core.domain

import java.time.LocalDateTime

class Team(
    var token: String = "",
    val creationDate: LocalDateTime = LocalDateTime.now(),
    var name: String,
    var logo: String? = null,
    var colors: List<String> = emptyList(),
    var pictures: List<String> = emptyList(),
    var licenceAmount: Float = 0F,
    var homeStadium: Stadium,
    var president: TeamMember,
    var coach: TeamMember? = null,
    var players: List<Player> = emptyList(),
    var rib: Rib? = null)

class Stadium(var name: String, var address: Address)

class Rib(var iban: String)