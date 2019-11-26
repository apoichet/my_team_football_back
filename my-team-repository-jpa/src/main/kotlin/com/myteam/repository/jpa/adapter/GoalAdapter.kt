package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.*
import com.myteam.core.enums.*

class GoalAdapter:
    RepositoryAdapter<Goal, com.myteam.repository.jpa.entities.Goal> {
    override fun convertDomainObjectToData(domainObject: Goal): com.myteam.repository.jpa.entities.Goal {
        return com.myteam.repository.jpa.entities.Goal(
            id = null,
            goalMember = domainObject.member.name,
            type = domainObject.type.name
        )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.Goal): Goal {
        return Goal(
            member = GoalMember.valueOf(data.goalMember),
            type = GoalType.valueOf(data.type)
        )
    }
}