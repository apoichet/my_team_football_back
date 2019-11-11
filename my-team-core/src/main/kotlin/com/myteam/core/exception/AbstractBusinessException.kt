package com.myteam.core.exception

import java.lang.Exception

abstract class AbstractBusinessException(message: String): Exception(message)