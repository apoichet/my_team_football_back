package com.myteam.domain

import com.myteam.enums.PaymentType
import com.myteam.enums.PlayerFoot
import com.myteam.enums.PlayerPosition
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
    var positions: List<PlayerPosition>,
    var playerContributions: List<PlayerContribution> = emptyList()
): TeamMember(
    contact,
    creationDate,
    bestQuality,
    worstDefault,
    verbatim
)

class PlayerContribution(val player: Player, val amount: Float, var paymentType: PaymentType)


