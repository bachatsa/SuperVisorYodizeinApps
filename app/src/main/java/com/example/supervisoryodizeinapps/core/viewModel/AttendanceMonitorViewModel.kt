package com.example.supervisoryodizeinapps.core.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supervisoryodizeinapps.R
import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.domain.model.attendanceMonitor.AttendanceMonitorCellModel
import com.example.supervisoryodizeinapps.core.domain.useCase.AttendanceHistoryUseCase
import com.example.supervisoryodizeinapps.core.extension.longToDateStr
import com.example.supervisoryodizeinapps.core.extension.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AttendanceMonitorViewModel @Inject constructor(
    private val useCase: AttendanceHistoryUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(AttendanceMonitorUIState())
    var uiState = _uiState.asStateFlow()

    init {
        getAttendances()
    }

    fun getAttendances() {
        useCase.getAttendances(_uiState.value.selectedDate)
            .onEach { result ->
                when (result) {
                    is ResourceState.SUCCESS -> {
                        _uiState.update {
                            it.copy(attendances = result.data!!.sortedByDescending { element ->
                                element.date.toDate("yyyy-MM-dd HH:mm:ss")
                            })
                        }
                        Log.d("getAttendances", "SUCCESS")
                    }

                    is ResourceState.ERROR -> {
                        Log.d("getAttendances", "ERROR")
                    }

                    is ResourceState.LOADING -> {
                        Log.d("getAttendances", "LOADING")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
    }
    fun convertDataToPdf() {
        viewModelScope.launch(Dispatchers.IO) {
            val attendances = _uiState.value.attendances
            val selectedDate = _uiState.value.selectedDate

            val outputPath = "Attendance_Report_$selectedDate.pdf"
            Log.d("ViewModelConvert",  generatePdf(attendances, selectedDate, outputPath).toString())
            generatePdf(attendances, selectedDate, outputPath)
        }
    }
    private suspend fun generatePdf(
        attendances: List<AttendanceMonitorCellModel>,
        selectedDate: String,
        outputPath: String
    ) {
        Log.d("ViewModelConvert",selectedDate)
        withContext(Dispatchers.IO) {
            val document = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(612, 792, 1).create()
            val page = document.startPage(pageInfo)
            val canvas = page.canvas

            val paint = Paint()

            // Draw title
            paint.textSize = 20f
            canvas.drawText("Attendance Report - $selectedDate", 50f, 50f, paint)

            // Draw table header
            paint.textSize = 14f
            canvas.drawText("Name", 50f, 100f, paint)
            canvas.drawText("Status", 200f, 100f, paint)
            canvas.drawText("Time", 350f, 100f, paint)

            // Draw table content
            paint.textSize = 12f
            var yPosition = 120f
            for (attendance in attendances) {
                canvas.drawText(attendance.userName, 50f, yPosition, paint)
                canvas.drawText(attendance.attendanceType, 200f, yPosition, paint)
                canvas.drawText(attendance.division, 350f, yPosition, paint)
                yPosition += 30f
            }
            Log.d("ViewModelConvert",page.toString())
            document.finishPage(page)

            try {
                val directory = File(Environment.getExternalStorageDirectory(), "YourAppDirectory")
                if (!directory.exists()) {
                    directory.mkdirs()
                }

                val filePath = File(directory, outputPath)
                val fileOutputStream = FileOutputStream(filePath)
                document.writeTo(fileOutputStream)
                document.close()
                fileOutputStream.close()
            } catch (e: IOException) {
                Log.d("ViewModelConvert",e.toString())
                e.printStackTrace()
            }
        }
    }

    fun onSelectedDateChanged(newValue: String) {
        _uiState.update {
            it.copy(
                selectedDate = newValue
            )
        }
        getAttendances()
    }
    fun clearToast() {
        _uiState.update {
            it.copy(
                toastMessages = ""
            )
        }
    }
}

data class AttendanceMonitorUIState(
    val attendances: List<AttendanceMonitorCellModel> = listOf(),
    val selectedDate: String = System.currentTimeMillis().longToDateStr(),
    val toastMessages: String = "",
)