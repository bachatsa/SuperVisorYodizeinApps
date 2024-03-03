package com.example.supervisoryodizeinapps.core.domain.useCase

import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.data.database.DatabaseRepositoryImpl
import com.example.supervisoryodizeinapps.core.domain.model.division.Division
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class HomeUseCase @Inject constructor(
    private val databaseRepositoryImpl: DatabaseRepositoryImpl
){
    fun getDivisions(): Flow<ResourceState<List<Division>>> = databaseRepositoryImpl.getDivisions()
}