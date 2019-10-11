package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
data class Goal (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @Column(nullable = false)
    val goalMember: String,

    @Column(nullable = false)
    val type: String

)