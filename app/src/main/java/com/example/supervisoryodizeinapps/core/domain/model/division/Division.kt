package com.ydzmobile.supervisor.core.domain.model.division

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ydzmobile.supervisor.core.domain.model.monitor.TargetModel

data class Division(
    val id: String = "",
    val name: String = "",
    var listOfTarget: List<TargetModel> = listOf()
)

data class DivisionResponse(
    val name: String = ""
)