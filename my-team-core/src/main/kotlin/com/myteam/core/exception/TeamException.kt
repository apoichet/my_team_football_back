package com.myteam.core.exception

class TeamAlreadyExists(msg: String): AbstractBusinessException(msg)

class TeamNotExists(msg: String): AbstractBusinessException(msg)