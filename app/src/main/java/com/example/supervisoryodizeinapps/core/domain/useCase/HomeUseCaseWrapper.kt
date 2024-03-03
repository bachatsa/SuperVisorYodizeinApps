package com.example.supervisoryodizeinapps.core.domain.useCase

import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.domain.model.division.Division
import kotlinx.coroutines.flow.Flow

class HomeUseCaseWrapper(private val realHomeUseCase: HomeUseCase){
    suspend fun getDivisions(): Flow<ResourceState<List<Division>>> {
        return realHomeUseCase.getDivisions()
    }
}