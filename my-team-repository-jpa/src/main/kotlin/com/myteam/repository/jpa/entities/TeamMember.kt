package com.myteam.repository.jpa.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "team_member")
data class TeamMember (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(nullable = false, name = "creation_date")
    val creationDate: LocalDateTime,

    @Column(nullable = false, name = "best_quality")
    val bestQuality: String,

    @Column(nullable = false, name = "worst_default")
    val worstDefault: String,

    @Column(nullable = false)
    val verbatim: String,

    @Column(nullable = false, name = "strong_foot")
    val strongFoot: String,

    @Column(nullable = false)
    val weight: Int,

    @Column(nullable = false)
    val height: Int,

    @Column(nullable = false, name = "original_position")
    val originalPosition: String,

    @OneToOne
    val contact: Contact,

    @OneToMany
    val positions: Collection<PlayerPosition>,

    @OneToMany
    val contributions: Collection<PlayerContribution>


)