package com.myteam.repository.jpa.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    val contact: Contact,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false, name = "creation_date")
    val creationDate: LocalDateTime,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn
    val teams: Collection<Team>



)