package com.myteam.repository.jpa.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @OneToOne(cascade = [CascadeType.PERSIST])
    val contact: Contact,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false, name = "creation_date")
    val creationDate: LocalDateTime,

    //@OneToMany
    //@JoinColumn
    @Transient
    val teams: Collection<Team>



)