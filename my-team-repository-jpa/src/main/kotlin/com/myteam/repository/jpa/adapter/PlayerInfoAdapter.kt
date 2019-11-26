package com.myteam.repository.jpa.adapter

import com.myteam.core.domain.PlayerInfo
import com.myteam.core.enums.*
import com.myteam.repository.jpa.entities.*
import com.myteam.repository.jpa.entities.PlayerPosition

class PlayerInfoAdapter:
    RepositoryAdapter<PlayerInfo, com.myteam.repository.jpa.entities.PlayerInfo> {
    override fun convertDomainObjectToData(domainObject: PlayerInfo): com.myteam.repository.jpa.entities.PlayerInfo {
        return PlayerInfo(
            id = null,
            strongFoot = domainObject.strongFoot.toString(),
            weight = domainObject.weight,
            height = domainObject.height,
            originalPosition = domainObject.originalPosition.toString(),
            otherPositions = domainObject.otherPositions?.map { o -> PlayerPosition(id = null, position = o.toString()) }
        )
    }

    override fun convertDataToDomainObject(data: com.myteam.repository.jpa.entities.PlayerInfo): PlayerInfo {
        return PlayerInfo(
            strongFoot = PlayerFoot.valueOf(data.strongFoot),
            weight = data.weight,
            height = data.height,
            originalPosition = com.myteam.core.enums.PlayerPosition.valueOf(data.originalPosition),
            otherPositions = data.otherPositions?.map { o -> com.myteam.core.enums.PlayerPosition.valueOf(o.position) }
        )
    }
}