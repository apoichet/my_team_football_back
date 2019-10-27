package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
data class Address (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @Column(nullable = false)
    val address: String,

    @Column(nullable = false)
    val city: String,

    @Column(nullable = false, name = "zip_code")
    val zipCode: String,

    @Column(nullable = false)
    val country: String

)