package com.myteam.repository.jpa.entities

import javax.persistence.*

@Entity
data class Stadium (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @Column(nullable = false)
    val name: String,

    @OneToOne
    val address: Address

)