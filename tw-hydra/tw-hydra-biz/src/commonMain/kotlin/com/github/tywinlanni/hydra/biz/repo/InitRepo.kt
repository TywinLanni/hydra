package com.github.tywinlanni.hydra.biz.repo

import com.github.tywinlanni.hydra.biz.exception.HydraProductDbNotConfiguredException
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.helpers.errorSystem
import com.github.tywinlanni.hydra.common.helpers.fail
import com.github.tywinlanni.hydra.common.models.HydraWorkMode
import com.github.tywinlanni.hydra.common.repo.IRepoProduct
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        productRepo = when (workMode) {
            HydraWorkMode.TEST -> corSettings.repoTest
            HydraWorkMode.STUB -> corSettings.repoStub
            else -> corSettings.repoProd
        }
        if (workMode != HydraWorkMode.STUB && productRepo == IRepoProduct.NONE) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = HydraProductDbNotConfiguredException(workMode)
            )
        )
    }
}
