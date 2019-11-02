package com.myteam.repository.jpa.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "team_member")
data class TeamMember (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,

    @Column(nullable = false, name = "creation_date")
    val creationDate: LocalDateTime,

    @Column(nullable = false, name = "best_quality")
    val bestQuality: String,

    @Column(nullable = false, name = "worst_default")
    val worstDefault: String,

    @Column(nullable = false)
    val verbatim: String,

    @Column(nullable = true, name = "strong_foot")
    val strongFoot: String? = null,

    @Column(nullable = true)
    val weight: Int? = null,

    @Column(nullable = true)
    val height: Int? = null,

    @Column(nullable = true, name = "original_position")
    val originalPosition: String? = null,

    @OneToOne
    val contact: Contact,

    @OneToMany
    val positions: Collection<PlayerPosition> = emptyList(),

    @OneToMany
    val contributions: Collection<PlayerContribution> = emptyList()


)