package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
data class PlayerPosition (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @Column(nullable = false)
    val position: String


)