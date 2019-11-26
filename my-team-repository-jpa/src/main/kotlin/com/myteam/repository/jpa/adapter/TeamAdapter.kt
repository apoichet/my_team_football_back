package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.Team
import com.myteam.repository.jpa.entities.*

class TeamAdapter:
    RepositoryAdapter<Team, com.myteam.repository.jpa.entities.Team> {

    private val gameAdapter = GameAdapter()
    private val stadiumAdapter = StadiumAdapter()
    private val ribAdapter = RibAdapter()
    private val userTeamAdapter = UserTeamAdapter()
    private val userTeamContributionAdapter = UserTeamContributionAdapter()

    override fun convertDomainObjectToData(domainObject: Team): com.myteam.repository.jpa.entities.Team {
        return Team(
            id = domainObject.token,
            creationDate = domainObject.creationDate,
            season = domainObject.season,
            name = domainObject.name,
            president = userTeamAdapter.convertDomainObjectToData(domainObject.president),
            coach = domainObject.coach?.let {userTeamAdapter.convertDomainObjectToData(it)},
            homeStadium = stadiumAdapter.convertDomainObjectToData(domainObject.homeStadium),
            licenceAmount = domainObject.licenceAmount,
            logo = domainObject.logo,
            rib = domainObject.rib?.let{ribAdapter.convertDomainObjectToData(it)},
            colors = domainObject.colors.map { c -> Color(codeHexa = c) },
            pictures = domainObject.pictures.map { p -> Picture(codeBase64 = p) },
            players = domainObject.players.map { p -> userTeamAdapter.convertDomainObjectToData(p) },
            contributions = domainObject.contributions.map { c ->
                userTeamContributionAdapter.convertDomainObjectToData(c) },
            games = domainObject.games.map { g -> gameAdapter.convertDomainObjectToData(g) }
        )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.Team): Team {
        return Team(
            token = data.id,
            creationDate = data.creationDate,
            season = data.season,
            name = data.name,
            president = userTeamAdapter.convertDataToDomainObject(data.president),
            coach = data.coach?.let {userTeamAdapter.convertDataToDomainObject(it)},
            homeStadium = stadiumAdapter.convertDataToDomainObject(data.homeStadium),
            licenceAmount = data.licenceAmount,
            logo = data.logo,
            rib = data.rib?.let{ribAdapter.convertDataToDomainObject(it)},
            colors = data.colors.map { c -> c.codeHexa },
            pictures = data.pictures.map { p -> p.codeBase64 },
            players = data.players.map { p -> userTeamAdapter.convertDataToDomainObject(p) },
            contributions = data.contributions.map { c ->
                userTeamContributionAdapter.convertDataToDomainObject(c) },
            games = data.games.map { g -> gameAdapter.convertDataToDomainObject(g) }
        )
    }

}