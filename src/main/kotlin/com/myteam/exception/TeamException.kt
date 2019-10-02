package com.myteam.exception

abstract class TeamException(msg:String): Throwable()

class TeamAlreadyExists(msg: String): TeamException(msg)

class TeamNotExists(msg: String): TeamException(msg)