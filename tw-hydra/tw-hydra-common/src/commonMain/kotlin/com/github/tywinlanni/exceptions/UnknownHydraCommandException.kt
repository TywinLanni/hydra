package com.github.tywinlanni.exceptions

import com.github.tywinlanni.models.HydraCommand

class UnknownHydraCommandException(command: HydraCommand) : Throwable("Wrong command $command at mapping toTransport stage")
