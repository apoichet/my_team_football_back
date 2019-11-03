package com.myteam.repository.jpa.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Team (

    @Id
    @GeneratedValue(generator = "uuid")
    @Column(name = "PR_KEY")
    val id: String,

    @Column(nullable = false, name = "creation_date")
    val creationDate: LocalDateTime,

    @Column(nullable = false)
    val season: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = true)
    val logo: String?,

    @Column(nullable = false, name = "licence_amount")
    val licenceAmount: Float,

    @OneToOne
    val president: TeamMember,

    @OneToOne
    val coach: TeamMember?,

    @OneToOne
    val homeStadium: Stadium,

    @OneToOne
    val rib: Rib?,

    @OneToMany
    val colors: Collection<Color>,

    @OneToMany
    val pictures: Collection<Picture>,

    @OneToMany
    @JoinColumn
    val players: Collection<TeamMember>

)