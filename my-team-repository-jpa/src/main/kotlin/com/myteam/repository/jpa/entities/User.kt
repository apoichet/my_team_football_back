package com.myteam.repository.jpa.entities

import java.time.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false, name = "creation_date")
    val creationDate: LocalDateTime,

    @OneToOne(cascade = [CascadeType.ALL])
    val userTeam: UserTeam,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn
    val teams: Collection<Team>

)