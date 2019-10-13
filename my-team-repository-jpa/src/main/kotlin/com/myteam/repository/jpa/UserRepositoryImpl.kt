package com.myteam.repository.jpa

import com.myteam.core.domain.User
import com.myteam.repository.UserRepository
import com.myteam.repository.jpa.adapter.UserAdapter
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.EntityTransaction
import javax.persistence.Persistence

class UserRepositoryImpl: UserRepository {

    private val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("my_team_pu")
    private val em: EntityManager = emf.createEntityManager()
    private val tx: EntityTransaction = em.transaction

    private val adapter = UserAdapter()

    override fun find(id: Any): User? {
        return adapter.convertDataToDomainObject(em.find(com.myteam.repository.jpa.entities.User::class.java, id))
    }

    override fun create(domainObject: User): User {
        val newUser = adapter.convertDomainObjectToData(domainObject)
        tx.begin()
        em.persist(newUser)
        tx.commit()
        return adapter.convertDataToDomainObject(newUser)
    }

    override fun update(domainObject: User): User {
        val userUpdated = adapter.convertDomainObjectToData(domainObject)
        em.merge(userUpdated)
        tx.begin()
        em.persist(userUpdated)
        tx.commit()
        return adapter.convertDataToDomainObject(userUpdated)
    }

    override fun delete(domainObject: User): Boolean {
        val userToDelete = adapter.convertDomainObjectToData(domainObject)
        em.merge(userToDelete)
        tx.begin()
        em.remove(userToDelete)
        tx.commit()
        return true
    }

    override fun findBy(mail: String): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}