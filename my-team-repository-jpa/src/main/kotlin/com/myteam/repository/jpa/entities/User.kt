package com.myteam.repository.jpa.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @OneToOne
    val contact: Contact,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val creationDate: LocalDateTime,

    @OneToMany
    val teams: Collection<Team>



)