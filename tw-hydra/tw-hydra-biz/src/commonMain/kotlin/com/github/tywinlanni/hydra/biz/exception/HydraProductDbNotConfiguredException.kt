package com.github.tywinlanni.hydra.biz.exception

import com.github.tywinlanni.hydra.common.models.HydraWorkMode

class HydraProductDbNotConfiguredException(val workMode: HydraWorkMode): Exception(
    "Database is not configured properly for work mode $workMode"
)
