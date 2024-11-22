package com.github.tywinlanni.hydra.common.repo.exceptions

import com.github.tywinlanni.hydra.common.models.HydraProductId

class RepoEmptyLockException(id: HydraProductId): RepoProductException(
    id,
    "Lock is empty in DB"
)
