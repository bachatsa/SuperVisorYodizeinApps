package com.ydzmobile.supervisor.core.domain.useCase

import com.ydzmobile.supervisor.core.data.ResourceState
import com.ydzmobile.supervisor.core.data.database.DatabaseRepositoryImpl
import com.ydzmobile.supervisor.core.domain.model.monitor.TargetModel
import com.ydzmobile.supervisor.core.domain.model.monitor.TargetModelCell
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DivisionDetailUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl
) {
    fun getTargets(division: String): Flow<ResourceState<List<TargetModelCell>>> = databaseRepositoryImpl.getTargets(division)
}