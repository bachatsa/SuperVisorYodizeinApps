package com.example.supervisoryodizeinapps.core.domain.model.division

import com.example.supervisoryodizeinapps.core.domain.model.monitor.TargetModel

data class Division(
    val id: String = "",
    val name: String = "",
    var listOfTarget: List<TargetModel> = listOf()
)

data class DivisionResponse(
    val name: String = ""
)