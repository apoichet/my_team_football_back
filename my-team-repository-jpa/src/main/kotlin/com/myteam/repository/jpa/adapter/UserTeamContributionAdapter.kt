package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.*
import com.myteam.core.enums.*

class UserTeamContributionAdapter:
    RepositoryAdapter<UserTeamContribution, com.myteam.repository.jpa.entities.UserTeamContribution> {

    private val userTeamAdapter = UserTeamAdapter()

    override fun convertDomainObjectToData(domainObject: UserTeamContribution): com.myteam.repository.jpa.entities.UserTeamContribution {
        return com.myteam.repository.jpa.entities.UserTeamContribution(
            id = null,
            amount = domainObject.amount,
            paymentType = domainObject.paymentType.name,
            userTeam = userTeamAdapter.convertDomainObjectToData(domainObject.userTeam)
        )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.UserTeamContribution): UserTeamContribution {
        return UserTeamContribution(
            amount = data.amount,
            paymentType = PaymentType.valueOf(data.paymentType),
            userTeam = userTeamAdapter.convertDataToDomainObject(data.userTeam)
        )
    }
}