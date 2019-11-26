package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
@Table(name = "player_position")
data class PlayerPosition (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,

    @Column(nullable = false)
    val position: String

)