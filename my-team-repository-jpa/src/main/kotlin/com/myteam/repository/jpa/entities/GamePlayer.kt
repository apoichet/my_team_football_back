package com.myteam.repository.jpa.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "game_player")
data class GamePlayer (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(nullable = true)
    val state: String,

    @Column(nullable = true)
    val arriving: LocalDateTime,

    @Column(nullable = true, name = "decisive_pass")
    val decisivePass: Int,

    @Column(nullable = true, name = "nb_yellow_card")
    val yellowCard: Int,

    @Column(nullable = true, name = "nb_red_card")
    val redCard: Int,

    @Column(nullable = true, name = "minute_referee")
    val minuteReferee: Int,

    @Column(nullable = true, name = "coach_rate")
    val coachRate: Int,

    @OneToMany
    @JoinColumn
    val positions: Collection<String>,

    @OneToMany
    @JoinColumn
    val goals: Collection<Goal>


)
