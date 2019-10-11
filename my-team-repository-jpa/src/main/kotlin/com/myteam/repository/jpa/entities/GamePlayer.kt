package com.myteam.repository.jpa.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class GamePlayer (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @Column(nullable = true)
    val state: String,

    @Column(nullable = true)
    val arriving: LocalDateTime,

    @Column(nullable = true)
    val decisivePass: Int,

    @Column(nullable = true)
    val yellowCard: Int,

    @Column(nullable = true)
    val redCard: Int,

    @Column(nullable = true)
    val minuteReferee: Int,

    @Column(nullable = true)
    val coachRate: Int,

    @OneToMany
    val positions: Collection<PlayerPosition>,

    @OneToMany
    val goals: Collection<Goal>


)
