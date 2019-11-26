package com.myteam.repository.jpa.entities

import java.time.*
import javax.persistence.*

@Entity
@Table(name = "user_team")
data class UserTeam(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @Column(nullable = false, name = "first_name")
    val firstName: String,

    @Column(nullable = false, name = "last_name")
    val lastName: String,

    @Column(nullable = false, name = "birth_date")
    val birthDate: LocalDate,

    @Column(nullable = true, name = "best_quality")
    val bestQuality: String,

    @Column(nullable = true, name = "worst_default")
    val worstDefault: String,

    @Column(nullable = true)
    val verbatim: String,

    @OneToOne(cascade = [CascadeType.ALL])
    val contact: Contact,

    @OneToOne
    val playerInfo: PlayerInfo?


)