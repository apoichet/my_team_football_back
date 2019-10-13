package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
data class Goal (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(nullable = false, name = "goal_member")
    val goalMember: String,

    @Column(nullable = false)
    val type: String

)