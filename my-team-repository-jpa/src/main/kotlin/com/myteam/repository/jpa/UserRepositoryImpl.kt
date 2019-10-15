package com.myteam.repository.jpa

import com.myteam.core.domain.User
import com.myteam.repository.UserRepository
import com.myteam.repository.jpa.adapter.UserAdapter
import javax.persistence.*

class UserRepositoryImpl(persistenceUnitName: String): UserRepository {

    private val emf: EntityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName)
    private val em: EntityManager = emf.createEntityManager()
    private val tx: EntityTransaction = em.transaction

    private val adapter = UserAdapter()

    override fun find(id: Any): User? {
        val userFound = em.find(com.myteam.repository.jpa.entities.User::class.java, id)
        return adapter.convertDataToDomainObject(userFound)
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
        return adapter.convertDataToDomainObject(userUpdated)
    }

    override fun delete(domainObject: User): Boolean {
        val userToDelete = em.find(com.myteam.repository.jpa.entities.User::class.java, domainObject.id)
        userToDelete?.let {
            tx.begin()
            em.remove(userToDelete)
            tx.commit()
            return true
        }
        return false
    }

    override fun findBy(mail: String): User? {
        val query = em.createQuery(
            "select user from User user where user.contact.mail = ?1"
        )
        query.setParameter(1, mail)
        val userFound = query.singleResult as(com.myteam.repository.jpa.entities.User)
        userFound.let {
            return adapter.convertDataToDomainObject(userFound)
        }
    }

    override fun findAll(): List<User> {
        val query = em.createQuery("select user from User user")
        return query.resultList.map {
            adapter.convertDataToDomainObject(it as (com.myteam.repository.jpa.entities.User))
        }
    }

}