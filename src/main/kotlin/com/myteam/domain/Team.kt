package com.myteam.domain

import java.time.LocalDateTime

data class Team(
    var token: String,
    var creationDate: LocalDateTime,
    var name: String,
    var logo: String? = null,
    var colors: List<String> = emptyList(),
    var pictures: List<String> = emptyList(),
    var licenceAmount: Float,
    var homeStadium: Stadium,
    var coach: Coach,
    var players: List<Player> = emptyList(),
    var playerContributions: List<PlayerContribution> = emptyList(),
    var rib: Rib? = null)

data class Stadium(var name: String, var adress: Adress)

data class PlayerContribution(val player: Player, val amount: Float, var paymentType: PaymentType)

enum class PaymentType {
    TRANSFERT, CASH, CHECK
}

data class Rib(var iban: String)