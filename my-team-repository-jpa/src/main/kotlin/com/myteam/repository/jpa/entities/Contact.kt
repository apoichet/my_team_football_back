package com.myteam.repository.jpa.entities

import java.time.LocalDate
import javax.persistence.*

@Entity
data class Contact(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(nullable = false, unique = true)
    val mail: String,

    @Column(nullable = false, name = "first_name")
    val firstName: String,

    @Column(nullable = false, name = "last_name")
    val lastName: String,

    @Column(nullable = false, name = "birth_date")
    val birthDate: LocalDate,

    @Column(nullable = false, name = "phone_number")
    val phoneNumber: String,

    @OneToMany(cascade = [CascadeType.PERSIST])
    val addresses: Collection<Address>


)