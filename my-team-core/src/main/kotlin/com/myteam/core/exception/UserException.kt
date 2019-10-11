package com.myteam.core.exception

abstract class UserException(msg: String) : Throwable()

class UserMailAlreadyExist(msg: String) : UserException(msg)

class UserAccountUnknown(msg: String) : UserException(msg)
