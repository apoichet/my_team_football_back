package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.Player
import com.myteam.repository.jpa.entities.TeamMember

class PlayerAdapter: RepositoryAdapter<Player, TeamMember> {
    override fun convertDomainObjectToData(domainObject: Player): TeamMember {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun convertDataToDomainObject(data: TeamMember): Player {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}