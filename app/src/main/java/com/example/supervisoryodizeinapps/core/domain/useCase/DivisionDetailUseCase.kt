package com.example.supervisoryodizeinapps.core.domain.useCase

import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.data.database.DatabaseRepositoryImpl
import com.example.supervisoryodizeinapps.core.domain.model.monitor.TargetModelCell
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DivisionDetailUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl
) {
    fun getTargets(division: String): Flow<ResourceState<List<TargetModelCell>>> = databaseRepositoryImpl.getTargets(division)
}