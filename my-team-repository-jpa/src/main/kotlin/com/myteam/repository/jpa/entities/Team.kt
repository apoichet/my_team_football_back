package com.myteam.repository.jpa.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Team (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int,

    @Column(nullable = false)
    val creationDate: LocalDateTime,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = true)
    val logo: String,

    @Column(nullable = false)
    val licenceAmount: Float,

    @OneToOne
    val president: TeamMember,

    @OneToOne
    val coach: TeamMember,

    @OneToOne
    val homeStadium: Stadium,

    @OneToOne
    val rib: Rib,

    @OneToMany
    val colors: Collection<Color>,

    @OneToMany
    val pictures: Collection<Picture>,

    @OneToMany
    val players: Collection<TeamMember>

)