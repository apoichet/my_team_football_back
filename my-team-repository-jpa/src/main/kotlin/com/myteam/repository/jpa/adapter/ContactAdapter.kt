package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.Contact

class ContactAdapter: RepositoryAdapter<Contact, com.myteam.repository.jpa.entities.Contact> {

    private val addressAdapter = AddressAdapter()

    override fun convertDomainObjectToData(domainObject: Contact): com.myteam.repository.jpa.entities.Contact {
        return com.myteam.repository.jpa.entities.Contact(
            mail = domainObject.mail,
            lastName = domainObject.lastName,
            firstName = domainObject.firstName,
            phoneNumber = domainObject.phone,
            birthDate = domainObject.birthDate,
            addresses = domainObject.addresses.map { a ->  addressAdapter.convertDomainObjectToData(a)}
        )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.Contact): Contact {
        return Contact(
            mail = data.mail,
            lastName = data.lastName,
            firstName = data.firstName,
            phone = data.phoneNumber,
            birthDate = data.birthDate,
            addresses = data.addresses.map { a ->  addressAdapter.convertDataToDomainObject(a)}
        )
    }

}