package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.Address

class AddressAdapter: RepositoryAdapter<Address, com.myteam.repository.jpa.entities.Address> {
    override fun convertDomainObjectToData(domainObject: Address): com.myteam.repository.jpa.entities.Address {
        return com.myteam.repository.jpa.entities.Address(
            address = domainObject.address,
            city = domainObject.city,
            zipCode = domainObject.zipCode,
            country = domainObject.country
        )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.Address): Address {
        return Address(
            address = data.address,
            city = data.city,
            zipCode = data.zipCode,
            country = data.country
        )
    }
}