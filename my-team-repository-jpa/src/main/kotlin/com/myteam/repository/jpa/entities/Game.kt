package com.myteam.repository.jpa.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Game (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(nullable = false, name = "name_opponent")
    val nameOpponent: String,

    @Column(nullable = false)
    val type: String,

    @Column(nullable = false)
    val place: String,

    @Column(nullable = false)
    val start: LocalDateTime,

    @Column(nullable = true, name = "rendez_vous")
    val rendezVous: LocalDateTime,

    @Column(nullable = false, name = "nb_goal_team")
    val nbGoalTeam: Int,

    @Column(nullable = false, name = "nb_goal_opponent")
    val nbrGoalOpponent: Int,

    @OneToOne
    val addressStadium: com.myteam.repository.jpa.entities.Address,

    @OneToMany
    @JoinColumn
    val gamePlayers: Collection<GamePlayer>

)