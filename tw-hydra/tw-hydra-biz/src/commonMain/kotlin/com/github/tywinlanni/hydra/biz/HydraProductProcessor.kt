package com.github.tywinlanni.hydra.biz

import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.HydraCommand
import com.github.tywinlanni.hydra.biz.general.initStatus
import com.github.tywinlanni.hydra.biz.general.operation
import com.github.tywinlanni.hydra.biz.general.stubs
import com.github.tywinlanni.hydra.biz.repo.*
import com.github.tywinlanni.hydra.biz.stubs.*
import com.github.tywinlanni.hydra.biz.validation.*
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.libs.cor.dsl.chain
import com.github.tywinlanni.libs.cor.dsl.rootChain
import com.github.tywinlanni.libs.cor.dsl.worker

class HydraProductProcessor(private val corSettings: HydraCorSettings) {
    
    suspend fun exec(ctx: HydraProductContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })
    
    private val businessChain = rootChain {
        initStatus("Инициализация статуса")
        initRepo("Инициализация репозитория")

        operation("Создание продукт", HydraCommand.CREATE) {
            stubs {
                stubCreateSuccess("Имитация успешной обработки")
                stubBadName("Имитация ошибки валидации имени")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase()
            }

            validation {
                validateName()
                finishProductValidation()
            }

            chain {
                title = "Логика сохранения"
                repoPrepareCreate("Подготовка объекта для сохранения")
                repoCreate("Создание объявления в БД")
            }

            prepareResult("Подготовка ответа")
        }

        operation("Получить продукт", HydraCommand.READ) {
            stubs {
                stubReadSuccess("Имитация успешной обработки")
                stubBadId("Имитация ошибки валидации id")
                stubNotFound("Имитация ошибки отсутствия продукта")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase()
            }

            validation {
                validateId()
                finishProductValidation()
            }

            chain {
                title = "Логика чтения"
                repoRead("Чтение объявления из БД")
                worker {
                    title = "Подготовка ответа для Read"
                    on { state == HydraState.RUNNING }
                    handle { productRepoDone = productRepoRead }
                }
            }

            prepareResult("Подготовка ответа")
        }

        operation("Изменить продукт", HydraCommand.UPDATE) {
            stubs {
                stubUpdateSuccess("Имитация успешной обработки")
                stubBadId("Имитация ошибки валидации id")
                stubBadName("Имитация ошибки валидации имени")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase()
            }

            validation {
                validateId()
                validateName()
                validateLock()
                finishProductValidation()
            }

            chain {
                title = "Логика сохранения"
                repoRead("Чтение объявления из БД")
                checkLock("Проверяем консистентность по оптимистичной блокировке")
                repoPrepareUpdate("Подготовка объекта для обновления")
                repoUpdate("Обновление объявления в БД")
            }

            prepareResult("Подготовка ответа")
        }

        operation("Удалить продукт", HydraCommand.DELETE) {
            stubs {
                stubDeleteSuccess("Имитация успешной обработки")
                stubBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubCannotDelete("Имитация ошибки удаления")
                stubNoCase()
            }

            validation {
                validateId()
                finishProductValidation()
            }

            chain {
                title = "Логика удаления"
                repoRead("Чтение объявления из БД")
                checkLock("Проверяем консистентность по оптимистичной блокировке")
                repoPrepareDelete("Подготовка объекта для удаления")
                repoDelete("Удаление объявления из БД")
            }

            prepareResult("Подготовка ответа")
        }

        operation("Поиск продуктов", HydraCommand.SEARCH) {
            stubs {
                stubSearchSuccess("Имитация успешной обработки")
                stubBadSearchString("Имитация ошибки некорректная строка поиска")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase()
            }

            validation {
                validateSearchString()
                finishProductFilterValidation()
            }

            repoSearch("Поиск объявления в БД по фильтру")
            prepareResult("Подготовка ответа")
        }
    }.build()
}
