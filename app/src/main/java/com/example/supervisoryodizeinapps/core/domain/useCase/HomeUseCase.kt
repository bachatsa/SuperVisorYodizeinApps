package com.ydzmobile.supervisor.core.domain.useCase

import com.ydzmobile.supervisor.core.data.ResourceState
import com.ydzmobile.supervisor.core.data.database.DatabaseRepositoryImpl
import com.ydzmobile.supervisor.core.domain.model.TargetListCellModel
import com.ydzmobile.supervisor.core.domain.model.division.Division
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl
){
    fun getDivisions(): Flow<ResourceState<List<Division>>> = databaseRepositoryImpl.getDivisions()
}