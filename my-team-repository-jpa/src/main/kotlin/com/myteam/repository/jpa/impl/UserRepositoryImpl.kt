package com.myteam.repository.jpa.impl

import com.myteam.core.domain.Contact
import com.myteam.core.domain.Team
import com.myteam.core.domain.User
import com.myteam.infra.UserRepository
import com.myteam.repository.jpa.adapter.ContactAdapter
import com.myteam.repository.jpa.adapter.TeamAdapter
import com.myteam.repository.jpa.adapter.UserAdapter
import javax.persistence.*

class UserRepositoryImpl(val em: EntityManager): UserRepository {

    private val userAdapater = UserAdapter()
    private val contactAdapter = ContactAdapter()
    private val teamAdapter = TeamAdapter()

    override fun create(newUser: User): User {
        val userData = userAdapater.convertDomainObjectToData(newUser)
        em.transaction.begin()
        em.persist(userData)
        em.transaction.commit()
        return userAdapater.convertDataToDomainObject(userData)
    }

    override fun updatePassword(userToModified: User, newPassword: String): User {
        find(userToModified.contact.mail)?.let {
            val userCopy = it.copy(password = newPassword)
            em.merge(userCopy)
            return userAdapater.convertDataToDomainObject(userCopy)
        }
        return userToModified
    }

    override fun updateContact(userToModified: User, newContact: Contact): User {
        find(userToModified.contact.mail)?.let {
            val userCopy = it.copy(contact = contactAdapter.convertDomainObjectToData(newContact))
            em.merge(userCopy)
            return userAdapater.convertDataToDomainObject(userCopy)
        }
        return userToModified
    }

    override fun delete(userToDelete: User): Boolean {
        find(userToDelete.contact.mail)?.let {
            em.transaction.begin()
            em.remove(it)
            em.transaction.commit()
            return true
        }
        return false
    }

    override fun findByMail(mail: String): User? {
        find(mail)?.let {
            return userAdapater.convertDataToDomainObject(it)
        }
        return null
    }

    override fun addTeam(userWithNewTeam: User, newTeam: Team): Team {
        find(userWithNewTeam.contact.mail)?.let {
            val dataTeam = teamAdapter.convertDomainObjectToData(newTeam)
            val userCopy = it.copy(teams =  it.teams.plus(dataTeam))
            em.merge(userCopy)
            return teamAdapter.convertDataToDomainObject(dataTeam)
        }
        return newTeam
    }


    private fun find(mail: String): com.myteam.repository.jpa.entities.User? {
        val query = em.createQuery("select user from User user where user.contact.mail = ?1")
        query.setParameter(1, mail)
        try {
            return query.singleResult as(com.myteam.repository.jpa.entities.User)
        }
        catch (e: NoResultException) {
            return null
        }
    }

}