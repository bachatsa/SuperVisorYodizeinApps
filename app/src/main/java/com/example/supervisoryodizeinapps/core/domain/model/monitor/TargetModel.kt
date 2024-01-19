package com.ydzmobile.supervisor.core.domain.model.monitor

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

@Parcelize
data class TargetModel(
    var id: String = "",
    var idEmployee: String = "",
    var idDivision: String = "",
    var dateStart: String = "",
    var dateFinish: String = "",
    var targetBeenDone: Int = 0,
    var totalTarget: Int = 0,
    var productType: String = "",
    var targetType: String = "",
): Parcelable

fun TargetModel.toUriString(): String {
    return Gson().toJson(this)
}

fun String.toTarget(): TargetModel? {
    return try {
        val type = object : TypeToken<TargetModel>() {}.type
        Gson().fromJson(this, type)
    } catch (e: Exception) {
        null
    }
}

data class TargetModelRequest(
    var idEmployee: String = "",
    var idDivision: String = "",
    var dateStart: String = "",
    var dateFinish: String = "",
    var targetBeenDone: Int = 0,
    var totalTarget: Int = 0,
    var productType: String = "",
    var targetType: String = "",
)

@Parcelize
data class TargetModelCell(
    var name: String = "",
    var target: TargetModel = TargetModel(),
): Parcelable