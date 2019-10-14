package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
data class Address (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(nullable = false)
    val address: String,

    @Column(nullable = false)
    val city: String,

    @Column(nullable = false, name = "zip_code")
    val zipCode: String,

    @Column(nullable = false)
    val country: String

)