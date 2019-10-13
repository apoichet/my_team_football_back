package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.User

class UserAdapter: RepositoryAdapter<User, com.myteam.repository.jpa.entities.User> {

    private val contactAdapter = ContactAdapter()
    private val teamAdapter = TeamAdapter()

    override fun convertDomainObjectToData(domainObject: User): com.myteam.repository.jpa.entities.User {
        return com.myteam.repository.jpa.entities.User(
            id = domainObject.id,
            password = domainObject.password,
            creationDate = domainObject.creationDate,
            contact = contactAdapter.convertDomainObjectToData(domainObject.contact),
            teams = domainObject.teams.map { t -> teamAdapter.convertDomainObjectToData(t) }
        )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.User): User {
        return User(
            id = data.id,
            password = data.password,
            creationDate = data.creationDate,
            contact = contactAdapter.convertDataToDomainObject(data.contact),
            teams = data.teams.map { t -> teamAdapter.convertDataToDomainObject(t) }
        )
    }

}