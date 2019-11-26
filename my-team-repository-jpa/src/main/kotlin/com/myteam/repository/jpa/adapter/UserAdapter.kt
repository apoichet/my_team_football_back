package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.*

class UserAdapter:
    RepositoryAdapter<User, com.myteam.repository.jpa.entities.User> {

    private val teamAdapter = TeamAdapter()
    private val userTeamAdapter = UserTeamAdapter()

    override fun convertDomainObjectToData(domainObject: User): com.myteam.repository.jpa.entities.User {
        return com.myteam.repository.jpa.entities.User(
            id = null,
            password = domainObject.password,
            creationDate = domainObject.creationDate,
            userTeam = userTeamAdapter.convertDomainObjectToData(domainObject.userTeam),
            teams = domainObject.teams.map { t -> teamAdapter.convertDomainObjectToData(t) }
        )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.User): User {
        return User(
            password = data.password,
            creationDate = data.creationDate,
            userTeam = userTeamAdapter.convertDataToDomainObject(data.userTeam),
            teams = data.teams.map { t -> teamAdapter.convertDataToDomainObject(t) }
        )
    }

}