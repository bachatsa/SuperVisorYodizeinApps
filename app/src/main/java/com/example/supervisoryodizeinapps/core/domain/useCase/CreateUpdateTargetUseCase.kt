package com.ydzmobile.supervisor.core.domain.useCase

import com.ydzmobile.supervisor.core.data.ResourceState
import com.ydzmobile.supervisor.core.data.database.DatabaseRepositoryImpl
import com.ydzmobile.supervisor.core.domain.model.auth.User
import com.ydzmobile.supervisor.core.domain.model.monitor.TargetModel
import com.ydzmobile.supervisor.core.domain.model.monitor.TargetModelRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUpdateTargetUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl
) {
    fun createTargets(division: String, target: TargetModelRequest): Flow<ResourceState<Boolean>> = databaseRepositoryImpl.createTargets(division, target)
    fun updateTarget(target: TargetModelRequest, idTarget: String): Flow<ResourceState<Boolean>> = databaseRepositoryImpl.updateTarget(target, idTarget)
    fun deleteTarget(target: TargetModelRequest, idTarget: String): Flow<ResourceState<Boolean>> = databaseRepositoryImpl.deleteTarget(target, idTarget)

    fun getUsers(idDivision: String): Flow<ResourceState<List<User>>> = databaseRepositoryImpl.getUsers(idDivision)
}