package com.github.tywinlanni.hydra.common.repo.exceptions

import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.models.HydraProductLock

class RepoConcurrencyException(id: HydraProductId, expectedLock: HydraProductLock, actualLock: HydraProductLock?): RepoProductException(
    id,
    "Expected lock is actualLock"
)
