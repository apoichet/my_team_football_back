package com.myteam.domain

import java.time.LocalDate

class Contact(
    var mail: String,
    var firstName: String,
    var lastName: String,
    var birthdate: LocalDate = LocalDate.of(1970, 1, 1),
    var phone: String = "",
    var adress: List<Adress> = emptyList()
)
