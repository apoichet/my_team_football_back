package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.TeamMember

class TeamMemberAdapter: RepositoryAdapter<TeamMember, com.myteam.repository.jpa.entities.TeamMember> {
    override fun convertDomainObjectToData(domainObject: TeamMember): com.myteam.repository.jpa.entities.TeamMember {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.TeamMember): TeamMember {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}