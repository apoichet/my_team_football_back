package com.myteam.repository

interface CrudRepository<O> {

    fun find(id: Any): O?

    fun findAll(): List<O>

    fun create(domainObject: O): O

    fun update(domainObject: O): O

    fun delete(domainObject: O): Boolean

}