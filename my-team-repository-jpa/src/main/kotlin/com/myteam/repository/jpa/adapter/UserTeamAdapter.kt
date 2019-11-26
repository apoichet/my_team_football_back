package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.*

class UserTeamAdapter:
    RepositoryAdapter<UserTeam, com.myteam.repository.jpa.entities.UserTeam> {

    private val contactAdapter = ContactAdapter()
    private val playerInfoAdapter = PlayerInfoAdapter()

    override fun convertDomainObjectToData(domainObject: UserTeam): com.myteam.repository.jpa.entities.UserTeam {
        return com.myteam.repository.jpa.entities.UserTeam(
            id = null,
            bestQuality = domainObject.bestQuality,
            worstDefault = domainObject.worstDefault,
            verbatim = domainObject.verbatim,
            contact = contactAdapter.convertDomainObjectToData(domainObject.contact),
            playerInfo = domainObject.playerInfo?.let { playerInfoAdapter.convertDomainObjectToData(it)},
            birthDate = domainObject.birthDate
        )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.UserTeam): UserTeam {
        return UserTeam(
            bestQuality = data.bestQuality,
            worstDefault = data.worstDefault,
            verbatim = data.verbatim,
            contact = contactAdapter.convertDataToDomainObject(data.contact),
            playerInfo = data.playerInfo?.let { playerInfoAdapter.convertDataToDomainObject(it)},
            birthDate = data.birthDate
        )
    }
}