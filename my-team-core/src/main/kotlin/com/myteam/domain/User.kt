package com.myteam.domain

import java.time.LocalDate
import java.time.LocalDateTime

data class User(
    val id: String = "",
    var mail: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var birthdate: LocalDate,
    var phone: String,
    var adress: List<Adress>,
    var teams: List<Team> = emptyList(),
    var creationDate: LocalDateTime = LocalDateTime.now()
)


