package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
data class Picture (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @Column
    val codeBase64: String

)