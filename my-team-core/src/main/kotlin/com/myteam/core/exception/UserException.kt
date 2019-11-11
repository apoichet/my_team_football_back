package com.myteam.core.exception

class UserMailAlreadyExist(msg: String) : AbstractBusinessException(msg)

class UserAccountUnknown(msg: String) : AbstractBusinessException(msg)
