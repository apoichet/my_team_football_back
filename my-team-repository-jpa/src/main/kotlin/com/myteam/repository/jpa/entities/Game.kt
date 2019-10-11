package com.myteam.repository.jpa.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Game (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    @Column(nullable = false)
    val nameOpponent: String,

    @Column(nullable = false)
    val type: String,

    @Column(nullable = false)
    val place: String,

    @Column(nullable = false)
    val start: LocalDateTime,

    @Column(nullable = true)
    val rendezVous: LocalDateTime,

    @Column(nullable = false)
    val nbrGoalTeam: Int,

    @Column(nullable = false)
    val nbrGoalOpponent: Int,

    @OneToOne
    val addressStadium: Address,

    @OneToMany
    val gamePlayers: Collection<GamePlayer>

)