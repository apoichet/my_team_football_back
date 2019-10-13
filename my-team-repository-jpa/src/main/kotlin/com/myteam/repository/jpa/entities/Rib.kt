package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
data class Rib (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(nullable = false, unique = true)
    val iban: String

)