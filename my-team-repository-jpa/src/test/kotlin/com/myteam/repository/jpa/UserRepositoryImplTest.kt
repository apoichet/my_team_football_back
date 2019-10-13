package com.myteam.repository.jpa

import com.myteam.core.domain.Address
import com.myteam.core.domain.Contact
import com.myteam.core.domain.User
import org.junit.jupiter.api.Test

internal class UserRepositoryImplTest {

    @Test
    fun test() {

        val userRepositoryImpl = UserRepositoryImpl()

        val address1 = Address(
            zipCode = "94000",
            address = "3 rue prout",
            city = "Paris"
        )

        val address2 = Address(
            zipCode = "75012",
            address = "3 rue chips",
            city = "Paris"
        )

        val contact = Contact(
            mail = "pipo",
            lastName = "lastName",
            firstName = "firstName",
            addresses = listOf(address1, address2)
        )

        val newUser = User(
            password = "mdp",
            contact = contact
        )

        System.out.println(newUser.id)

        userRepositoryImpl.create(newUser)

        val test = userRepositoryImpl.find(1)

        System.out.println(test?.id)
    }

}


