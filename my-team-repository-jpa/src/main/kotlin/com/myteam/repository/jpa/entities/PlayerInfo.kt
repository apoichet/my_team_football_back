package com.myteam.repository.jpa.entities

import javax.persistence.*


@Entity
@Table(name = "player_info")
data class PlayerInfo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @Column(nullable = true, name = "strong_foot")
    val strongFoot: String,

    @Column(nullable = true)
    val weight: Int?,

    @Column(nullable = true)
    val height: Int?,

    @Column(nullable = true)
    val originalPosition: String,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn
    val otherPositions: Collection<PlayerPosition>?

)