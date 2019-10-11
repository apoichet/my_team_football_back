package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
data class PlayerContribution (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @Column(nullable = false)
    val amount: Float,

    @Column(nullable = false)
    val type: String

)