package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.Rib

class RibAdapter: RepositoryAdapter<Rib, com.myteam.repository.jpa.entities.Rib> {
    override fun convertDomainObjectToData(domainObject: Rib): com.myteam.repository.jpa.entities.Rib {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.Rib): Rib {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}