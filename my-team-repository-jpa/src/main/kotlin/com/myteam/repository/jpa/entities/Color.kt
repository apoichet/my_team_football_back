package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
data class Color (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(nullable = false, name = "code_hexa")
    val codeHexa: String

)