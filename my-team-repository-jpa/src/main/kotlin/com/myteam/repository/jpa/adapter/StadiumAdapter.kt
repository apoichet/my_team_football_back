package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.Stadium

class StadiumAdapter: RepositoryAdapter<Stadium, com.myteam.repository.jpa.entities.Stadium> {
    override fun convertDomainObjectToData(domainObject: Stadium): com.myteam.repository.jpa.entities.Stadium {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.Stadium): Stadium {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}