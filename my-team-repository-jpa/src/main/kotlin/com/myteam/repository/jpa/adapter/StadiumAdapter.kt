package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.Stadium

class StadiumAdapter:
    RepositoryAdapter<Stadium, com.myteam.repository.jpa.entities.Stadium> {

    private val addressAdapter = AddressAdapter()

    override fun convertDomainObjectToData(domainObject: Stadium): com.myteam.repository.jpa.entities.Stadium {
        return com.myteam.repository.jpa.entities.Stadium(
            address = addressAdapter.convertDomainObjectToData(domainObject.address),
            name = domainObject.name
        )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.Stadium): Stadium {
        return Stadium(
            address = addressAdapter.convertDataToDomainObject(data.address),
            name = data.name
        )
    }
}