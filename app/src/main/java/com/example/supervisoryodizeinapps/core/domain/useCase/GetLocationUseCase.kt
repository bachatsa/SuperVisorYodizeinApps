package com.example.supervisoryodizeinapps.core.domain.useCase

import com.google.android.gms.maps.model.LatLng
import com.example.supervisoryodizeinapps.core.utils.ILocationService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationService: ILocationService
) {
    operator fun invoke(): Flow<LatLng?> = locationService.requestLocationUpdates()

}