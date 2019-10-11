package com.myteam.repository.jpa.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class TeamMember (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @Column(nullable = false)
    val creationDate: LocalDateTime,

    @Column(nullable = false)
    val bestQuality: String,

    @Column(nullable = false)
    val worstDefault: String,

    @Column(nullable = false)
    val verbatim: String,

    @Column(nullable = false)
    val strongFoot: String,

    @Column(nullable = false)
    val weight: Int,

    @Column(nullable = false)
    val height: Int,

    @Column(nullable = false)
    val originalPosition: String,

    @OneToOne
    val contact: Contact,

    @OneToMany
    val positions: Collection<PlayerPosition>,

    @OneToMany
    val contributions: Collection<PlayerContribution>














)