package com.example.supervisoryodizeinapps


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.supervisoryodizeinapps.core.data.ResourceState
import com.example.supervisoryodizeinapps.core.data.database.DatabaseRepositoryImpl

import com.example.supervisoryodizeinapps.core.domain.model.division.Division
import com.example.supervisoryodizeinapps.core.domain.useCase.HomeUseCase
import com.example.supervisoryodizeinapps.core.viewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations




class YourViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel // Replace with the actual name of your ViewModel class
//    @Mock
//    private lateinit var firebaseAuth: FirebaseAuth
//    @Mock
//    private lateinit var firebaseFirestore: FirebaseFirestore
    @Mock
    private lateinit var databaseRepositoryImpl: DatabaseRepositoryImpl
    private lateinit var homeUseCase: HomeUseCase // Replace with the actual name of your UseCase class
    private val testDispatcher = StandardTestDispatcher()



    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
         homeUseCase = HomeUseCase(databaseRepositoryImpl)
        viewModel = HomeViewModel(homeUseCase) // Initialize your ViewModel with the mocked UseCase
    }

    @Test
    fun `getDivisions should update UI state on success`() = runBlocking {
        // Arrange
        val successResult = ResourceState.SUCCESS(listOf(Division("1oS0XazzfSuDd5Reu8Lh", "penjahit" ), Division("6e7uVedCp4dbpc2nMOvJ", "Penjahit")))
        `when`(homeUseCase.getDivisions()).thenReturn(flowOf(successResult)) // Use your actual type for successResult

        // Act
        viewModel.getDivisions()
//        testDispatcher.scheduler.advanceUntilIdle()
        // Assert
        // Verify that the UI state is updated correctly
        // Example: assertEquals(expectedData, viewModel.uiState.value.divisionList)
    }

    @Test
    fun `getDivisions should handle error state`() = runBlocking {
        // Arrange
        val errorResult = ResourceState.ERROR("Data tidak ditemukan",listOf<Division>(Division("", ""), Division("", "",)))
        `when`(homeUseCase.getDivisions()).thenReturn(flowOf(errorResult)) // Use your actual type for errorResult

        // Act
        viewModel.getDivisions()

        // Assert
        // Verify that the error state is handled correctly in the UI
        // Example: assertTrue(viewModel.uiState.value is ErrorState)
    }

    @Test
    fun `getDivisions should handle loading state`() = runBlocking {
        // Arrange
        val loadingResult = ResourceState.LOADING(listOf<Division>(Division("6e7uVedCp4dbpc2nMOvJ", "penjahit"), Division("EyQpDsUnJMgrFNVfiydj", "Pemotong",)))
        `when`(homeUseCase.getDivisions()).thenReturn(flowOf(loadingResult))

        // Act
        viewModel.getDivisions()

        // Assert
        // Verify that the loading state is handled correctly in the UI
        // Example: assertTrue(viewModel.uiState.value is LoadingState)
    }
//    @After
//    fun close() {
//        Dispatchers.shutdown()
//    }
}
