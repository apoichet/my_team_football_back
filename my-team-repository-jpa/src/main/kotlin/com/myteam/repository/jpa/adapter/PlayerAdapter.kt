package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.Player
import com.myteam.core.enums.PlayerFoot
import com.myteam.core.enums.PlayerPosition
import com.myteam.repository.jpa.entities.TeamMember

class PlayerAdapter: RepositoryAdapter<Player, TeamMember> {

    private val contactAdapter = ContactAdapter()

    override fun convertDomainObjectToData(domainObject: Player): TeamMember {
        return TeamMember(
            id = null,
            creationDate = domainObject.creationDate,
            bestQuality = domainObject.bestQuality,
            worstDefault = domainObject.worstDefault,
            verbatim = domainObject.verbatim,
            strongFoot = domainObject.strongFoot.name,
            weight = domainObject.weight,
            height = domainObject.height,
            originalPosition = domainObject.originalPosition.name,
            contact = contactAdapter.convertDomainObjectToData(domainObject.contact),
            positions = domainObject.positions.map { playerPosition ->
                com.myteam.repository.jpa.entities.PlayerPosition(id = null, position = playerPosition.name)}
        )
    }

    override fun convertDataToDomainObject(data: TeamMember): Player {
        return Player(
            creationDate = data.creationDate,
            bestQuality = data.bestQuality,
            worstDefault = data.worstDefault,
            verbatim = data.verbatim,
            strongFoot = PlayerFoot.valueOf(data.strongFoot?:let { it }.run { "" }),
            weight = data.weight?.let { it }.run { 0 },
            height = data.height?.let { it }.run { 0 },
            originalPosition = PlayerPosition.valueOf(data.originalPosition?:let { it }.run { "" }),
            contact = contactAdapter.convertDataToDomainObject(data.contact),
            positions = data.positions.map { PlayerPosition.valueOf(data.originalPosition?:let { it }.run { "" })}
        )
    }


}