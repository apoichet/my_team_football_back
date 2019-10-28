package com.myteam.repository.jpa.impl

import com.myteam.core.domain.*

internal class TeamRepositoryImplTest : RepositoryImplTest() {

    private val sut = TeamRepositoryImpl(super.em)

    private fun buildTeam(token: String, mailPresident: String): Team {
        return Team(
            token = token,
            name = "name",
            president = buildTeamMember(mailPresident),
            homeStadium = buildStadium()
        )
    }

    private fun buildTeamMember(mail: String): TeamMember{
        return TeamMember(
            contact = buildContact(mail)
        )
    }

    private fun buildStadium(): Stadium {
        return Stadium(name = "name", address = buildAddress("2 rue du stade"))
    }

    private fun buildAddress(address: String): Address {
        return Address(address , "city", "zip_code", "country")
    }

    private fun buildContact(mail: String): Contact {
        return Contact(mail, "firtsname", "lastname")
    }





}