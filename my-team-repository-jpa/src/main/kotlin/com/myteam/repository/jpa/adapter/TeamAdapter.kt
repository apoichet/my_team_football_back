package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.Team
import com.myteam.repository.jpa.entities.Color
import com.myteam.repository.jpa.entities.Picture

class TeamAdapter:
    RepositoryAdapter<Team, com.myteam.repository.jpa.entities.Team> {

    private val teamMemberAdapter = TeamMemberAdapter()
    private val playerAdapter = PlayerAdapter()
    private val stadiumAdapter = StadiumAdapter()
    private val ribAdapter = RibAdapter()

    override fun convertDomainObjectToData(domainObject: Team): com.myteam.repository.jpa.entities.Team {
        return com.myteam.repository.jpa.entities.Team(
            id = domainObject.token,
            creationDate = domainObject.creationDate,
            season = domainObject.season,
            name = domainObject.name,
            president = teamMemberAdapter.convertDomainObjectToData(domainObject.president),
            coach = domainObject.coach?.let {teamMemberAdapter.convertDomainObjectToData(it)},
            homeStadium = stadiumAdapter.convertDomainObjectToData(domainObject.homeStadium),
            licenceAmount = domainObject.licenceAmount,
            logo = domainObject.logo,
            rib = domainObject.rib?.let{ribAdapter.convertDomainObjectToData(it)},
            colors = domainObject.colors.map { c -> Color(codeHexa = c) },
            pictures = domainObject.pictures.map { p -> Picture(codeBase64 = p) },
            players = domainObject.players.map { p -> playerAdapter.convertDomainObjectToData(p) }
        )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.Team): Team {
        return Team(
            token = data.id,
            creationDate = data.creationDate,
            season = data.season,
            name = data.name,
            president = teamMemberAdapter.convertDataToDomainObject(data.president),
            coach = data.coach?.let {teamMemberAdapter.convertDataToDomainObject(it)},
            homeStadium = stadiumAdapter.convertDataToDomainObject(data.homeStadium),
            licenceAmount = data.licenceAmount,
            logo = data.logo,
            rib = data.rib?.let{ribAdapter.convertDataToDomainObject(it)},
            colors = data.colors.map { c -> c.codeHexa },
            pictures = data.pictures.map { p -> p.codeBase64 },
            players = data.players.map { p -> playerAdapter.convertDataToDomainObject(p) }
        )
    }

}