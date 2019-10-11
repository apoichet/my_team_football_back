package com.myteam.repository.jpa

import com.myteam.core.domain.Team
import com.myteam.repository.TeamRepository

class TeamRepositoryImpl: TeamRepository  {

    override fun find(id: String): Team? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): List<Team> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun create(domainObject: Team): Team {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(domainObject: Team): Team {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(domainObject: Team): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}