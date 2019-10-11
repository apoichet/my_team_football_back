package com.myteam.core.domain

import java.time.LocalDate
import java.time.LocalDateTime

class User(
    val id: String = "",
    var contact: Contact,
    var password: String,
    var teams: List<Team> = emptyList(),
    var creationDate: LocalDateTime = LocalDateTime.now()
)

class Contact(
    var mail: String,
    var firstName: String,
    var lastName: String,
    var birthdate: LocalDate = LocalDate.of(1970, 1, 1),
    var phone: String = "",
    var addresses: List<Address> = emptyList()
)

class Address(var adress: String,
              var city: String,
              var zipCode: String,
              var country: String = "France")


