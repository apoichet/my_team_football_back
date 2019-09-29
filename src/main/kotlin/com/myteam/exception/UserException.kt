package com.myteam.exception

abstract class UserException(val msg: String): Throwable()

class UserMailAlreadyExist(msg: String) : UserException(msg)

class UserAccountUnknown(msg: String) : UserException(msg)
