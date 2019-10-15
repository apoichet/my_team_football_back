package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.TeamMember

class TeamMemberAdapter: RepositoryAdapter<TeamMember, com.myteam.repository.jpa.entities.TeamMember> {

    private val contactAdapter = ContactAdapter()

    override fun convertDomainObjectToData(domainObject: TeamMember): com.myteam.repository.jpa.entities.TeamMember {
       return com.myteam.repository.jpa.entities.TeamMember(
           contact = contactAdapter.convertDomainObjectToData(domainObject.contact),
           creationDate = domainObject.creationDate,
           bestQuality = domainObject.bestQuality,
           verbatim = domainObject.verbatim,
           worstDefault = domainObject.worstDefault
       )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.TeamMember): TeamMember {
        return TeamMember(
            contact = contactAdapter.convertDataToDomainObject(data.contact),
            creationDate = data.creationDate,
            bestQuality = data.bestQuality,
            verbatim = data.verbatim,
            worstDefault = data.worstDefault
        )
    }
}