package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
data class Picture (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(name = "code_base_64")
    val codeBase64: String

)