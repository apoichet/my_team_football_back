package com.myteam.domain

import java.time.LocalDate
import java.time.LocalDateTime

class User(
    val id: String = "",
    var contact: Contact,
    var password: String,
    var teams: List<Team> = emptyList(),
    var creationDate: LocalDateTime = LocalDateTime.now()
)


