package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
@Table(name = "player_contribution")
data class UserTeamContribution (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,

    @Column(nullable = false)
    val amount: Float,

    @Column(nullable = false)
    val paymentType: String,

    @OneToOne
    val userTeam: UserTeam

)