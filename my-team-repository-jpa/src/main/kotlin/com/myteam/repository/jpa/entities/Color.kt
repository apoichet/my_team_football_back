package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
data class Color (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @Column
    val codeHexa: String

)