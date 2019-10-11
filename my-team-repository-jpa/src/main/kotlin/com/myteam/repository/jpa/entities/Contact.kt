package com.myteam.repository.jpa.entities

import java.time.LocalDate
import javax.persistence.*

@Entity
data class Contact(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @Column(nullable = false, unique = true)
    val mail: String,

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(nullable = false)
    val birthDate: LocalDate,

    @Column(nullable = false)
    val phoneNumber: String,

    @OneToMany
    val addresses: Collection<Address>


)