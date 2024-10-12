package com.github.tywinlanni.hydra.common.exceptions

import com.github.tywinlanni.hydra.common.models.HydraCommand

class UnknownHydraCommandException(command: HydraCommand) : Throwable("Wrong command $command at mapping toTransport stage")
