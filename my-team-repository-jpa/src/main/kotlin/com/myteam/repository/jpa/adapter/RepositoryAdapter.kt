package com.myteam.repository.jpa.adapter

interface RepositoryAdapter<O, D> {

    fun convertDomainObjectToData(domainObject: O): D

    fun convertDataToDomainObject(data: D): O

}