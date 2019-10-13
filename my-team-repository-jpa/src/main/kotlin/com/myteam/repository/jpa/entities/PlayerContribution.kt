package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
@Table(name = "player_contribution")
data class PlayerContribution (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(nullable = false)
    val amount: Float,

    @Column(nullable = false)
    val type: String

)